package com.akerusan.todolist

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_button.setOnClickListener(this)

        val adapter = NoteAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        noteViewModel.getAllNotes().observe(this, Observer{ notes ->
            adapter.submitList(notes)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val deletedNote = adapter.getNoteAt(viewHolder.adapterPosition)
                noteViewModel.delete(deletedNote)
                val snackBar = Snackbar
                    .make(layout_coordinator, "Note deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        noteViewModel.insert(deletedNote)
                        val snackBarUndo = Snackbar.make(layout_coordinator, "Note is restored!", Snackbar.LENGTH_SHORT)
                        snackBarUndo.show()
                    }
                snackBar.show()
            }
        }).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener{
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
                intent.putExtra("id", note.id)
                intent.putExtra("title", note.title)
                intent.putExtra("description", note.description)
                intent.putExtra("priority", note.priority)
                startActivity(intent)
            }
        })
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                val notesBackup = noteViewModel.getAllNotes().value!!
                noteViewModel.deleteAllNotes()
                val snackBar = Snackbar
                    .make(layout_coordinator, "All notes deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        for (each in notesBackup) {
                            noteViewModel.insert(each)
                        }
                        val snackBarUndo = Snackbar.make(layout_coordinator, "Notes are restored!", Snackbar.LENGTH_SHORT)
                        snackBarUndo.show()
                    }
                snackBar.show()
                true
            }
            R.id.settings -> {
                // Do nothing yet
                Toast.makeText(this@MainActivity, "Coming Soon", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}