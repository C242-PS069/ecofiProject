package com.dicoding.ecofiproject.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dicoding.ecofiproject.R
import com.dicoding.ecofiproject.ui.login.LoginActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_screen)

        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("OnboardingPrefs", MODE_PRIVATE)

        val imageSlider = findViewById<ImageSlider>(R.id.onboarding_image)
        val skipButton = findViewById<Button>(R.id.skip_button)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.onboarding_1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.onboarding_2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.onboarding_3, ScaleTypes.FIT))
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        skipButton.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun finishOnboarding() {
        sharedPreferences.edit().putBoolean("OnboardingCompleted", true).apply()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
