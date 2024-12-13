package com.dicoding.ecofiproject.ui.scan

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.data.UserRepository
import com.dicoding.ecofiproject.data.api.ApiConfig
import com.dicoding.ecofiproject.data.pref.UserPreference
import com.dicoding.ecofiproject.data.pref.dataStore
import com.dicoding.ecofiproject.ui.recommend.RecommendActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

class ScanFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var btnGallery: Button
    private lateinit var btnScan: Button
    private lateinit var btnKreasikan: Button
    private lateinit var userRepository: UserRepository
    private var imageFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageView)
        btnGallery = view.findViewById(R.id.buttonGallery)
        btnScan = view.findViewById(R.id.buttonScan)
        btnKreasikan = view.findViewById(R.id.buttonKreasikan)

        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        userRepository = UserRepository.getInstance(userPreference)

        val galleryLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    imageView.setImageURI(it)
                    imageFile = File(requireContext().cacheDir, "gallery_image.jpg")
                    val inputStream = requireContext().contentResolver.openInputStream(it)
                    val outputStream = FileOutputStream(imageFile)
                    inputStream?.copyTo(outputStream)
                    inputStream?.close()
                    outputStream.close()
                    Log.d("DEBUG_IMAGE", "Image selected: ${imageFile?.absolutePath}")
                }
            }

        val cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
                bitmap?.let {
                    imageView.setImageBitmap(it)
                    imageFile = File(requireContext().cacheDir, "camera_image.jpg")
                    val outputStream = FileOutputStream(imageFile)
                    it.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                    Log.d("DEBUG_CAMERA", "Image captured: ${imageFile?.absolutePath}")
                }
            }

        btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        btnScan.setOnClickListener {
            cameraLauncher.launch()
        }

        btnKreasikan.setOnClickListener {
            if (imageFile != null && imageFile!!.exists()) {
                lifecycleScope.launch {
                    val user = userRepository.getSession().first()
                    if (user.isLogin) {
                        Log.d("DEBUG_TOKEN", "Sending token: ${user.token}")
                        submitImage(user.token)
                    } else {
                        Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "No image selected or file not found", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private suspend fun submitImage(token: String) {
        imageFile?.let { file ->
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val imagePart = MultipartBody.Part.createFormData("image", file.name, requestBody)

            val apiService = ApiConfig.getApiService()
            val call = apiService.predictImage("Bearer $token", imagePart)

            call.enqueue(object :
                retrofit2.Callback<com.dicoding.ecofiproject.data.response.PredictionResponse> {
                override fun onResponse(
                    call: retrofit2.Call<com.dicoding.ecofiproject.data.response.PredictionResponse>,
                    response: retrofit2.Response<com.dicoding.ecofiproject.data.response.PredictionResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        responseBody?.let {
                            val predict = it.predict
                            println("DEBUG_API: $it")
                            val dataItem : ArrayList<com.dicoding.ecofiproject.data.response.DataItem> = it.data as ArrayList<com.dicoding.ecofiproject.data.response.DataItem>

                            if (predict != null) {
                                val intent = Intent(requireContext(), RecommendActivity::class.java).apply {
                                    putExtra("MATERIAL", predict.label)  // Label material yang diprediksi
                                    putExtra("CONFIDENCE", predict.confident)  // Confidence level prediksi
                                    putParcelableArrayListExtra("ITEMLIST", dataItem)  // Judul produk
                                }
                                startActivity(intent)
                            } else {
                                Toast.makeText(context, "Prediction failed", Toast.LENGTH_SHORT).show()
                            }

                        }
                    } else {
                        Log.e("DEBUG_API", "Error: ${response.errorBody()?.string()}")
                        Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<com.dicoding.ecofiproject.data.response.PredictionResponse>,
                    t: Throwable
                ) {
                    Log.e("DEBUG_API_FAILURE", "Request failed: ${t.message}")
                    Toast.makeText(context, "Request failed: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}