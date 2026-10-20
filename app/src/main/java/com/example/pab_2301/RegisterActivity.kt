package com.example.pab_2301

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var rootContainer: View
    private lateinit var topBar: View
    private lateinit var rootScrollView: ScrollView
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
        setupKeyboardHandling()
        setupListeners()
    }

    private fun initViews() {
        rootContainer = findViewById(R.id.rootContainer)
        topBar = findViewById(R.id.topBar)
        rootScrollView = findViewById(R.id.rootScrollView)
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

    private fun setupKeyboardHandling() {
        // Menangani Insets: TopBar aman dari notch status bar, dan ScrollView menyesuaikan tinggi keyboard
        ViewCompat.setOnApplyWindowInsetsListener(rootContainer) { _, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = windowInsets.getInsets(WindowInsetsCompat.Type.ime())

            // Atur padding atas topBar agar tidak tertimpa status bar
            topBar.updatePadding(
                left = systemBars.left,
                top = systemBars.top,
                right = systemBars.right
            )

            // Atur padding bawah scrollview setinggi keyboard atau navigasi
            val bottomInset = if (ime.bottom > 0) ime.bottom else systemBars.bottom
            rootScrollView.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                bottom = bottomInset
            )

            windowInsets
        }

        // Memastikan field yang sedang difokuskan otomatis ter-scroll ke tengah layar
        val focusListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                rootScrollView.postDelayed({
                    val rect = Rect()
                    view.getDrawingRect(rect)
                    rootScrollView.offsetDescendantRectToMyCoords(view, rect)
                    rect.bottom += 120
                    rootScrollView.requestChildRectangleOnScreen(view, rect, true)
                }, 200)
            }
        }

        edtName.onFocusChangeListener = focusListener
        edtEmail.onFocusChangeListener = focusListener
        edtPassword.onFocusChangeListener = focusListener
        edtConfirmPassword.onFocusChangeListener = focusListener
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
