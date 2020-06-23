package com.akerusan.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.note_item.*

class AddNoteActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels()
    private var editMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        add_priority.minValue = 1
        add_priority.maxValue = 10

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

        val intent = intent
        if (intent.hasExtra("id")) { editMode = true }
        if (editMode) {
            title = "Edit Note"
            add_title.setText(intent.getStringExtra("title"))
            add_description.setText(intent.getStringExtra("description"))
            add_priority.value = intent.getIntExtra("priority", 1)
        } else {
            title = "Add Note"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.save_note) {
            saveNotes()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveNotes() {
        val title = add_title.text.toString()
        val description = add_description.text.toString()
        val priority = add_priority.value

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title or description", Toast.LENGTH_SHORT).show()
            return
        } else {
            if(!editMode){
                noteViewModel.insert(Note(title, description, priority))
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val updatedNote = Note(title, description, priority)
                updatedNote.id = intent.getIntExtra("id", -1)
                noteViewModel.update(updatedNote)
                Toast.makeText(this, "Note Edited", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}