package com.akerusan.todolist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("DELETE FROM note_db")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_db ORDER BY priority DESC")
    fun getAllNotes() : LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

}