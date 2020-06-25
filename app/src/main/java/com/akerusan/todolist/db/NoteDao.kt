package com.akerusan.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.akerusan.todolist.db.Note

@Dao
interface NoteDao {

    @Query("DELETE FROM note_db")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_db ORDER BY date DESC")
    fun getAllNotes() : LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

}