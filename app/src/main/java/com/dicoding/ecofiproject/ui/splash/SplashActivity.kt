//package com.dicoding.ecofiproject.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.ecofiproject.ui.login.LoginActivity
import com.dicoding.ecofiproject.MainActivity
import com.dicoding.ecofiproject.utils.SessionManager

//class SplashActivity : AppCompatActivity() {

  //  override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)

        // Mengecek status login menggunakan SessionManager
      //  val sessionManager = SessionManager(this)
        //if (sessionManager.isLoggedIn()) {
            // Jika sudah login, langsung ke MainActivity
          //  startActivity(Intent(this, MainActivity::class.java))
        //} else {
            // Jika belum login, ke LoginActivity
          //  startActivity(Intent(this, LoginActivity::class.java))
        //}

        // Menutup SplashActivity agar tidak kembali setelah masuk ke activity lain
        //finish()
    //}
//}