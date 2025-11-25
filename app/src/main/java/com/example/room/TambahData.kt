package com.example.room

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.room.database.Note
import com.example.room.database.NoteRoomDatabase
import com.example.room.helper.DateHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahData : AppCompatActivity() {

    private lateinit var _etJudul: TextInputEditText
    private lateinit var _etDeskripsi: TextInputEditText
    private lateinit var _btnTambah: MaterialButton
    private lateinit var _btnUpdate: MaterialButton

    private val DB: NoteRoomDatabase by lazy {
        NoteRoomDatabase.getDatabase(this)
    }

    private var tanggal: String = DateHelper.getCurrentDate()
    private var iID: Int = 0
    private var iAddEdit: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambah_data)

        _etJudul = findViewById(R.id.etJudul)
        _etDeskripsi = findViewById(R.id.etDeskripsi)
        _btnTambah = findViewById(R.id.btnTambah)
        _btnUpdate = findViewById(R.id.btnUpdate)

        iID = intent.getIntExtra("noteId", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) { // Tambah Data
            _btnTambah.visibility = View.VISIBLE
            _btnUpdate.visibility = View.GONE
            _etJudul.isEnabled = true
        } else { // Edit Data
            _btnTambah.visibility = View.GONE
            _btnUpdate.visibility = View.VISIBLE
            _etJudul.isEnabled = false

            // Mengambil data lama di background (Coroutine)
            CoroutineScope(Dispatchers.IO).async {
                val noteItem: Note = DB.funnoteDao().getNote(iID)
                _etJudul.setText(noteItem.judul)
                _etDeskripsi.setText(noteItem.deskripsi)
            }
        }

        // --- Operasi Tambah Data (Create) ---
        _btnTambah.setOnClickListener {
            // Operasi database di background thread (Dispatchers.IO)
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().insert(
                    Note (
                        id = 0,
                        judul = _etJudul.text.toString(),
                        deskripsi = _etDeskripsi.text.toString(),
                        tanggal = tanggal
                    )
                )
                // Kembali ke halaman sebelumnya
                finish()
            }
        }

        // --- Operasi Edit Data (Update) ---
        _btnUpdate.setOnClickListener {
            // Operasi database di background thread (Dispatchers.IO)
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().update(
                    _etJudul.text.toString(),
                    _etDeskripsi.text.toString(),
                    iID
                )
                // Kembali ke halaman sebelumnya
                finish()
            }
        }
    }
}