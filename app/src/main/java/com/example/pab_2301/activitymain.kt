package com.example.pab_2301

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class activitymain : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)

        val rootScrollView = findViewById<View>(R.id.rootScrollView)
        ViewCompat.setOnApplyWindowInsetsListener(rootScrollView) { view, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            view.updatePadding(
                left = insets.left,
                top = insets.top,
                right = insets.right,
                bottom = insets.bottom
            )
            windowInsets
        }

        val edtNama = findViewById<EditText>(R.id.edtNama)
        val rgjk = findViewById<RadioGroup>(R.id.rgjk)
        val spkota = findViewById<Spinner>(R.id.spkota)
        val swmenyetujui = findViewById<Switch>(R.id.swmenyutujui)
        val btnsimpan = findViewById<Button>(R.id.btn_simpan)

        val kota = arrayOf("Pilih Kota", "Jakarta", "Bandung", "Surabaya", "Padang")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, kota)
        spkota.adapter = adapter

        btnsimpan.setOnClickListener {
            // Perbaikan edtNama (huruf N kapital)
            if (edtNama.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Nama Tidak Boleh Kosong!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!swmenyetujui.isChecked) {
                Toast.makeText(this, "Anda harus menyutujui S&K", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val jk = when (rgjk.checkedRadioButtonId) {
                R.id.rdlaki -> "Laki-Laki"
                R.id.rdperempuan -> "Perempuan"
                else -> "Belum Dipilih"
            }

            val data = "Nama : ${edtNama.text}\nJenis Kelamin : $jk\nKota : ${spkota.selectedItem}"


            val intent = Intent(this, DuaActivity::class.java)
            intent.putExtra("DATA HASIL", data)
            startActivity(intent)
        }
    }
}
