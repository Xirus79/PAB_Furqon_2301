package com.example.pab_2301

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var tilEmail: TextInputLayout
    private lateinit var edtEmail: TextInputEditText
    private lateinit var tilPassword: TextInputLayout
    private lateinit var edtPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvGoToRegister: TextView

    // Menerima hasil dari RegisterActivity jika registrasi berhasil
    private val registerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val registeredEmail = result.data?.getStringExtra(EXTRA_REGISTERED_EMAIL)
            if (!registeredEmail.isNullOrEmpty()) {
                edtEmail.setText(registeredEmail)
                edtPassword.requestFocus()
                Toast.makeText(this, "Registrasi berhasil! Silakan masukkan kata sandi", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        tilEmail = findViewById(R.id.tilEmail)
        edtEmail = findViewById(R.id.edtEmail)
        tilPassword = findViewById(R.id.tilPassword)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        tvGoToRegister = findViewById(R.id.tvGoToRegister)
    }

    private fun setupListeners() {
        // Tombol Masuk / Login
        btnLogin.setOnClickListener {
            handleLogin()
        }

        // Navigasi ke halaman Registrasi
        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            registerLauncher.launch(intent)
        }

        // Navigasi ke halaman Reset Password
        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            val currentEmail = edtEmail.text.toString().trim()
            if (currentEmail.isNotEmpty()) {
                intent.putExtra(EXTRA_EMAIL, currentEmail)
            }
            startActivity(intent)
        }
    }

    private fun handleLogin() {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        tilEmail.error = null
        tilPassword.error = null

        var isValid = true

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

        if (!isValid) return

        // Simulasi Login Berhasil
        Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

        // Pindah ke Halaman Utama (HomeActivity)
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra(EXTRA_EMAIL, email)
        }
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_EMAIL = "EXTRA_EMAIL"
        const val EXTRA_REGISTERED_EMAIL = "EXTRA_REGISTERED_EMAIL"
    }
}
