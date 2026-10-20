package com.example.pab_2301

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tilName: TextInputLayout
    private lateinit var edtName: TextInputEditText
    private lateinit var tilEmail: TextInputLayout
    private lateinit var edtEmail: TextInputEditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var edtPassword: TextInputEditText
    private lateinit var tilConfirmPassword: TextInputLayout
    private lateinit var edtConfirmPassword: TextInputEditText
    private lateinit var btnRegister: MaterialButton
    private lateinit var tvGoToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        tilName = findViewById(R.id.tilName)
        edtName = findViewById(R.id.edtName)
        tilEmail = findViewById(R.id.tilEmail)
        edtEmail = findViewById(R.id.edtEmail)
        tilPassword = findViewById(R.id.tilPassword)
        edtPassword = findViewById(R.id.edtPassword)
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword)
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)
        tvGoToLogin = findViewById(R.id.tvGoToLogin)
    }

    private fun setupListeners() {
        // Tombol Kembali
        btnBack.setOnClickListener {
            finish()
        }

        // Teks "Sudah punya akun? Masuk"
        tvGoToLogin.setOnClickListener {
            finish()
        }

        // Tombol Daftar
        btnRegister.setOnClickListener {
            handleRegister()
        }
    }

    private fun handleRegister() {
        val name = edtName.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()

        tilName.error = null
        tilEmail.error = null
        tilPassword.error = null
        tilConfirmPassword.error = null

        var isValid = true

        if (name.isEmpty()) {
            tilName.error = "Nama lengkap tidak boleh kosong"
            isValid = false
        }

        if (email.isEmpty()) {
            tilEmail.error = "Email tidak boleh kosong"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Format email tidak valid"
            isValid = false
        }

        if (password.isEmpty()) {
            tilPassword.error = "Kata sandi tidak boleh kosong"
            isValid = false
        } else if (password.length < 6) {
            tilPassword.error = "Kata sandi minimal 6 karakter"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            tilConfirmPassword.error = "Konfirmasi kata sandi tidak boleh kosong"
            isValid = false
        } else if (password != confirmPassword) {
            tilConfirmPassword.error = "Konfirmasi kata sandi tidak cocok"
            isValid = false
        }

        if (!isValid) return

        // Registrasi berhasil: kirim data email kembali ke LoginActivity
        val returnIntent = Intent().apply {
            putExtra(LoginActivity.EXTRA_REGISTERED_EMAIL, email)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        Toast.makeText(this, "Registrasi atas nama $name berhasil!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
