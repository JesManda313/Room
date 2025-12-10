package com.example.room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.room.database.NoteRoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var db : NoteRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = NoteRoomDatabase.getDatabase(this)
        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }



    }

    override fun onStart(){
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val note = db.noteDao().selectAll()
            Log.d("data Room", note.toString())
        }
    }
}
