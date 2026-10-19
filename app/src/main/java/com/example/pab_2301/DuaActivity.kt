package com.example.pab_2301

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class DuaActivity : AppCompatActivity() {

    private var progress = 0
    private val handler = Handler(Looper.getMainLooper())
    @Volatile private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dua)

        val pbloading = findViewById<ProgressBar>(R.id.pbloading)
        val tvhasil = findViewById<TextView>(R.id.tvhasil)
        val btnkembali = findViewById<Button>(R.id.btnkembali)

        val data = intent.getStringExtra("DATA HASIL")
        tvhasil.text = data

        thread {
            while (progress < 100 && isRunning) {
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progress++
                val p = progress
                handler.post {
                    if (isRunning) pbloading.progress = p
                }
            }
        }

        btnkembali.setOnClickListener {
            val intent = Intent(this, activitymain::class.java) // Sesuaikan nama kelas Activity utama kamu
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}