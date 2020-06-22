package com.akerusan.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel.getAllNotes().observe(this, Observer{
                notes -> Toast.makeText(this, "onChanged", Toast.LENGTH_LONG).show()// update recyclerView
        })
    }
}