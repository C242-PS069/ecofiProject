package com.dicoding.ecofiproject.ui.scan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.ecofiproject.databinding.FragmentScanBinding

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private val CAMERA_REQUEST_CODE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tombol untuk memulai kamera
        binding.buttonScan.setOnClickListener {
            startCamera()
        }

        // Tombol untuk membuka galeri
        binding.buttonGallery.setOnClickListener {
            openGallery()
        }
    }

    // Fungsi untuk memulai kamera
    private fun startCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }
    }

    // Fungsi untuk membuka galeri
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    // Menangani hasil dari kamera atau galeri
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                // Menangani hasil dari kamera
                val imageBitmap = data?.extras?.get("data") as? Bitmap
                // Lakukan sesuatu dengan gambar (misalnya menampilkan di ImageView)
            } else if (requestCode == REQUEST_GALLERY) {
                // Menangani hasil dari galeri
                val imageUri = data?.data
                // Lakukan sesuatu dengan gambar (misalnya menampilkan di ImageView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_GALLERY = 1001
    }
}
