import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore
import com.dicoding.ecofiproject.data.response.PredictResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ScanFragment : Fragment() {

    private lateinit var userRepository: UserRepository
    private lateinit var imgPreview: ImageView
    private lateinit var tvPrediction: TextView
    private var selectedImageBitmap: Bitmap? = null
    private var currentPhotoPath: String? = null

    companion object {
        private const val REQUEST_IMAGE_SELECT = 100
        private const val REQUEST_IMAGE_CAPTURE = 101
        private const val REQUEST_PERMISSION = 102
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UserRepository
        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        userRepository = UserRepository.getInstance(userPreference)

        // Initialize UI elements
        imgPreview = view.findViewById(R.id.imageView)
        tvPrediction = view.findViewById(R.id.quoteTextView)
        val btnSelectImage: Button = view.findViewById(R.id.buttonGallery)
        val btnCaptureImage: Button = view.findViewById(R.id.buttonScan)
        val btnCreate: Button = view.findViewById(R.id.buttonKreasikan)

        btnSelectImage.setOnClickListener {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                selectImageFromGallery()
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        btnCaptureImage.setOnClickListener {
            if (checkPermission(Manifest.permission.CAMERA)) {
                captureImageWithCamera()
            } else {
                requestPermission(Manifest.permission.CAMERA)
            }
        }

        btnCreate.setOnClickListener {
            selectedImageBitmap?.let {
                uploadImage(it)
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), REQUEST_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retry the action
                when (permissions[0]) {
                    Manifest.permission.READ_EXTERNAL_STORAGE -> selectImageFromGallery()
                    Manifest.permission.CAMERA -> captureImageWithCamera()
                }
            }
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_SELECT)
    }

    private fun captureImageWithCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            try {
                val photoFile = createImageFile()
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "${requireContext().packageName}.provider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } catch (ex: IOException) {
                // Handle error while creating file
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = requireContext().getExternalFilesDir(null)!!
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_SELECT -> {
                    val imageUri: Uri? = data?.data
                    imageUri?.let {
                        val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
                        imgPreview.setImageBitmap(bitmap)
                        imgPreview.visibility = View.VISIBLE
                        selectedImageBitmap = bitmap
                        uploadImage(bitmap)
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val file = File(currentPhotoPath!!)
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, Uri.fromFile(file))
                    imgPreview.setImageBitmap(bitmap)
                    imgPreview.visibility = View.VISIBLE
                    selectedImageBitmap = bitmap
                }
            }
        }
    }

    private fun uploadImage(bitmap: Bitmap) {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()

        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), bitmapData)
        val body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)

        userRepository.predictImage(body).enqueue(object : Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                if (response.isSuccessful) {
                    val prediction = response.body()?.predict
                    tvPrediction.text = "Prediction: ${prediction?.label} (Confidence: ${prediction?.confident})"
                } else {
                    tvPrediction.text = "Prediction failed: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                tvPrediction.text = "Prediction failed: ${t.message}"
            }
        })
    }
}
