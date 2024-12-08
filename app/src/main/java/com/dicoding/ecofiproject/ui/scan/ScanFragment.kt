package com.dicoding.ecofiproject.ui.scan

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.ui.recommend.RecommendActivity

class ScanFragment : Fragment() {

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.imageView) // Ganti dengan ID ImageView Anda
        val btnGallery = view.findViewById<Button>(R.id.buttonGallery) // Tombol galeri
        val btnScan = view.findViewById<Button>(R.id.buttonScan)       // Tombol scan/kamera
        val btnKreasikan = view.findViewById<Button>(R.id.buttonKreasikan) // Tombol "Buat"

        // Handler untuk galeri
        val galleryLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    imageView.setImageURI(it) // Tampilkan gambar ke ImageView
                }
            }

        // Handler untuk kamera
        val cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
                bitmap?.let {
                    imageView.setImageBitmap(it) // Tampilkan gambar ke ImageView
                }
            }

        // Aksi tombol galeri
        btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*") // Memilih gambar dari galeri
        }

        // Aksi tombol kamera
        btnScan.setOnClickListener {
            cameraLauncher.launch() // Memotret dengan kamera
        }

        // Aksi tombol "Buat" untuk membuka RecommendActivity
        btnKreasikan.setOnClickListener {
            val intent = Intent(requireContext(), RecommendActivity::class.java)
            startActivity(intent) // Buka RecommendActivity
        }
    }
}
