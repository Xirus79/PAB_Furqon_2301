package com.example.pab_2301

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    private lateinit var rootContainer: View
    private lateinit var topBar: View
    private lateinit var rootScrollView: View
    private lateinit var tvWelcomeTitle: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var btnOpenForm: MaterialButton
    private lateinit var btnLogout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        setupInsets()
        setupData()
        setupListeners()
    }

    private fun initViews() {
        rootContainer = findViewById(R.id.rootContainer)
        topBar = findViewById(R.id.topBar)
        rootScrollView = findViewById(R.id.rootScrollView)
        tvWelcomeTitle = findViewById(R.id.tvWelcomeTitle)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        btnOpenForm = findViewById(R.id.btnOpenForm)
        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(rootContainer) { _, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = windowInsets.getInsets(WindowInsetsCompat.Type.ime())

            topBar.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right
            )

            val bottomInset = if (ime.bottom > 0) ime.bottom else systemBars.bottom
            rootScrollView.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                bottom = bottomInset
            )

            windowInsets
        }
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
