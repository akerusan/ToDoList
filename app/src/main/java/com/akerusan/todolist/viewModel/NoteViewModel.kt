package com.akerusan.todolist.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.akerusan.todolist.db.Note
import com.akerusan.todolist.NoteRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        repository = NoteRepository(application)
        allNotes = repository!!.getAllNotes()
    }

    fun insert(note: Note) {
        GlobalScope.launch { repository!!.insert(note) }
    }

    fun update(note: Note) {
        GlobalScope.launch { repository!!.update(note) }
    }

    fun delete(note: Note) {
        GlobalScope.launch { repository!!.delete(note) }
    }

    fun deleteAllNotes() {
        GlobalScope.launch { repository!!.deleteAllNotes() }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes!!
    }
}