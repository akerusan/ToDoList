package com.akerusan.todolist

import android.app.Application
import androidx.lifecycle.LiveData
import com.akerusan.todolist.db.Note
import com.akerusan.todolist.db.NoteDao
import com.akerusan.todolist.db.NoteDatabase

class NoteRepository(application: Application) {

    private var noteDao: NoteDao? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        val db = NoteDatabase.getInstance(application)
        noteDao = db!!.noteDao()
        allNotes = noteDao!!.getAllNotes()
    }

    suspend fun insert(note: Note){
        noteDao!!.insert(note)
    }

    suspend fun update(note: Note){
        noteDao!!.update(note)
    }

    suspend fun delete(note: Note){
        noteDao!!.delete(note)
    }

    suspend fun deleteAllNotes(){
        noteDao!!.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes!!
    }

}