package com.example.pab_2301

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

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var rootContainer: View
    private lateinit var topBar: View
    private lateinit var rootScrollView: ScrollView
    private lateinit var btnBack: ImageButton
    private lateinit var tilEmail: TextInputLayout
    private lateinit var edtEmail: TextInputEditText
    private lateinit var btnResetPassword: MaterialButton
    private lateinit var tvBackToLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        initViews()
        setupKeyboardHandling()
        setupListeners()
        loadInitialData()
    }

    private fun initViews() {
        rootContainer = findViewById(R.id.rootContainer)
        topBar = findViewById(R.id.topBar)
        rootScrollView = findViewById(R.id.rootScrollView)
        btnBack = findViewById(R.id.btnBack)
        tilEmail = findViewById(R.id.tilEmail)
        edtEmail = findViewById(R.id.edtEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        tvBackToLogin = findViewById(R.id.tvBackToLogin)
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
        edtEmail.setOnFocusChangeListener { view, hasFocus ->
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
