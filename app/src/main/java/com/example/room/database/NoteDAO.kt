package com.example.room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(not: Note)

    @Query(value = "UPDATE note SET judul=:isi_judul, deskripsi=:isi_deskripsi WHERE id=:isi_id")
    suspend fun update(isi_judul: String, isi_deskripsi: String, isi_id: Int)

    @Delete
    suspend fun delete(note: Note)

    @Query(value = "SELECT * FROM note ORDER BY id asc")
    suspend fun selectAll(): MutableList<Note>

    @Query("SELECT * FROM note WHERE id=:isi_id")
    suspend fun getNote(isi_id: Int): Note
}