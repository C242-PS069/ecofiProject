package com.dicoding.ecofiproject.ui.recommend

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.R
import com.bumptech.glide.Glide

class RecommendDetailActivity : AppCompatActivity() {

    private lateinit var itemTitle: TextView
    private lateinit var itemImage: ImageView
    private lateinit var itemDescription: TextView
    private lateinit var extraInformation: TextView
    private lateinit var extraDetails: TextView
    private lateinit var recommendActionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_recommend)

        // Inisialisasi Views
        itemTitle = findViewById(R.id.item_title)
        itemImage = findViewById(R.id.item_image)
        itemDescription = findViewById(R.id.item_description)
        extraInformation = findViewById(R.id.extra_information)
        extraDetails = findViewById(R.id.extra_details)
        recommendActionButton = findViewById(R.id.button_recommend_action)

        // Set data (misalnya mengambil data dari intent atau API)
        itemTitle.text = "Cara Membuat Tempat Pensil"
        itemDescription.text = """
            Alat dan Bahan yang Dibutuhkan:
            
            - Botol bekas
            - Spidol
            - Kertas pelangi
            - Lem
            - Gunting atau cutter.

            Langkah-langkah cara membuatnya:

            1. Potong botol yang akan digunakan sebagai wadah. Buang sisa potongan botol yang tidak digunakan.
            2. Buat penutup botol atau sampul dari kertas pelangi. Sampul botol menggunakan kertas pelangi hingga semua sisi tertutup.
            3. Buat pola-pola unik menggunakan kertas pelangi. Contohnya, kamu dapat menggambar alis, mata, hidung, dan mulut.
            4. Pasang bentuk tersebut pada tempat pensil. Hal tersebut akan membuat wadah pensil menjadi seperti karakter kartun.
            5. Tempat pensil yang lucu dan unik siap untuk digunakan.
        """.trimIndent()

        extraInformation.text = "Informasi Tambahan:"
        extraDetails.text = "Tempat pensil ini cocok digunakan untuk berbagai keperluan sekolah dan kantor, dan akan membuat meja kerja Anda lebih berwarna."

        // Menggunakan Glide untuk memuat gambar (pastikan URL atau gambar lokal sesuai)
        Glide.with(this)
            .load("https://example.com/pencil_holder_image.jpg")  // Ganti dengan URL atau gambar lokal
            .placeholder(R.drawable.ic_place_holder)  // Placeholder sementara
            .error(R.drawable.ic_error)  // Gambar error jika gagal memuat
            .into(itemImage)

        // Tombol untuk tindakan rekomendasi
        recommendActionButton.setOnClickListener {
            // Tindakan untuk tombol rekomendasi (misalnya, menyimpan atau berbagi)
            // Anda bisa menambahkan aksi lainnya di sini
        }
    }
}
