package com.example.pab_2301

import android.os.Bundle
import android.util.Patterns
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tilEmail: TextInputLayout
    private lateinit var edtEmail: TextInputEditText
    private lateinit var btnResetPassword: MaterialButton
    private lateinit var tvBackToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        initViews()
        setupListeners()
        loadInitialData()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tilEmail = findViewById(R.id.tilEmail)
        edtEmail = findViewById(R.id.edtEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
    }

    private fun loadInitialData() {
        // Ambil email dari intent jika ada
        val emailFromLogin = intent.getStringExtra(LoginActivity.EXTRA_EMAIL)
        if (!emailFromLogin.isNullOrEmpty()) {
            edtEmail.setText(emailFromLogin)
        }
    }

    private fun setupListeners() {
        // Tombol Kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Teks "Kembali ke Halaman Masuk"
        tvBackToLogin.setOnClickListener {
            finish()
        }

        // Tombol Kirim Link Pemulihan
        btnResetPassword.setOnClickListener {
            handleResetPassword()
        }
    }

    private fun handleResetPassword() {
        val email = edtEmail.text.toString().trim()

        tilEmail.error = null

        if (email.isEmpty()) {
            tilEmail.error = "Email tidak boleh kosong"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Format email tidak valid"
            return
        }

        // Simulasi pengiriman link reset password
        Toast.makeText(
            this,
            "Tautan reset kata sandi telah dikirim ke $email. Silakan periksa inbox Anda.",
            Toast.LENGTH_LONG
        ).show()

        // Selesaikan activity dan kembali ke Login
        finish()
    }
}
