package com.akerusan.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch { // launch a new coroutine in background and continue
            getDb()
        }
    }

    private fun getDb(){
        val db =
            Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "note_table")
                .fallbackToDestructiveMigration()
                .build()

        val noteDao = db.noteDao()
        val newNote = Note(1, "Titre", "Description", 1)
        noteDao.insert(newNote)
        Log.v("TAG", "after insert ${noteDao.getAllNotes().toString()}")

    }
}