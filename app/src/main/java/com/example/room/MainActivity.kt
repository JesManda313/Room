package com.example.room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.room.database.NoteRoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var db: NoteRoomDatabase
    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = NoteRoomDatabase.getDatabase(this)
        fabAdd = findViewById(R.id.fabAdd)
        fabAdd.setOnClickListener {
            val intent = Intent(this, TambahData::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().selectAll()
            withContext(Dispatchers.Main) {
                Log.d("MainActivity", "Data Notes yang ditemukan:")

                if (notes.isEmpty()) {
                    Log.d("MainActivity", "Tidak ada data dalam database.")
                } else {
                    notes.forEach { note ->
                        Log.d("MainActivity", "ID: ${note.id}, Judul: ${note.judul}, Deskripsi: ${note.deskripsi}, Tanggal: ${note.tanggal}")
                    }
                }
            }
        }
    }
}