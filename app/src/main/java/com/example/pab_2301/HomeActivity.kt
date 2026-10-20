package com.example.pab_2301

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    private lateinit var tvWelcomeTitle: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var btnOpenForm: MaterialButton
    private lateinit var btnLogout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupData()
        setupListeners()
    }

    private fun initViews() {
        tvWelcomeTitle = findViewById(R.id.tvWelcomeTitle)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        btnOpenForm = findViewById(R.id.btnOpenForm)
        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun setupData() {
        val userEmail = intent.getStringExtra(LoginActivity.EXTRA_EMAIL) ?: "pengguna@example.com"
        val username = userEmail.substringBefore("@")
        tvWelcomeTitle.text = "Selamat Datang, $username!"
        tvUserEmail.text = "Masuk sebagai: $userEmail"
    }

    private fun setupListeners() {
        // Buka Formulir Pendaftaran (activitymain - modul praktikum sebelumnya)
        btnOpenForm.setOnClickListener {
            val intent = Intent(this, activitymain::class.java)
            startActivity(intent)
        }

        // Logout dan bersihkan task stack, kembali ke Login
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Berhasil keluar dari akun", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}
