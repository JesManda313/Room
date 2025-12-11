package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.room.database.Note
import com.example.room.database.NoteRoomDatabase
import com.example.room.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext // Ditambahkan untuk withContext

class TambahData : AppCompatActivity() {

    private lateinit var etJudul: EditText
    private lateinit var etDeskripsi: EditText
    private lateinit var btnTambah: Button
    private lateinit var btnUpdate: Button

    private lateinit var db: NoteRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambah_data)

        db = NoteRoomDatabase.getDatabase(this)

        etJudul = findViewById(R.id.etJudul)
        etDeskripsi = findViewById(R.id.etDeskripsi)
        btnTambah = findViewById(R.id.btnTambah)
        btnUpdate = findViewById(R.id.btnUpdate)

        val tanggal : String  = getCurrentDate()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnTambah.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().insert(
                    Note(
                        id = 0,
                        judul = etJudul.text.toString(),
                        deskripsi = etDeskripsi.text.toString(),
                        tanggal = tanggal
                    )
                )
                withContext(Dispatchers.Main) {
                    finish()
                }
            }
        }
    }
}