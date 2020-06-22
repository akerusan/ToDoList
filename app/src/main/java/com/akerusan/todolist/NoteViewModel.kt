package com.akerusan.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        repository = NoteRepository(application)
        allNotes = repository!!.getAllNotes()
    }

    suspend fun insert(note: Note){
        repository!!.insert(note)
    }

    suspend fun update(note: Note){
        repository!!.update(note)
    }

    suspend fun delete(note: Note){
        repository!!.delete(note)
    }

    suspend fun deleteAllNotes(){
        repository!!.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes!!
    }
}