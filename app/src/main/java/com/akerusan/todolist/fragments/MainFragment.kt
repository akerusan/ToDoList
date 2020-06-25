package com.akerusan.todolist.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akerusan.todolist.db.Note
import com.akerusan.todolist.NoteAdapter
import com.akerusan.todolist.viewModel.NoteViewModel
import com.akerusan.todolist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), View.OnClickListener {

    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        requireActivity().title = "To Do List"

        navController = Navigation.findNavController(view)
        view.findViewById<FloatingActionButton>(R.id.add_button).setOnClickListener(this)

        val adapter = NoteAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        noteViewModel.getAllNotes().observe(viewLifecycleOwner, Observer{ notes ->
            adapter.submitList(notes)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
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

        adapter.setOnItemClickListener(object :
            NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val bundle = bundleOf(
                    "title" to note.title,
                    "description" to note.description,
                    "date" to note.date,
                    "id" to note.id)

                navController.navigate(
                    R.id.action_mainFragment_to_addNoteFragment, bundle)
            }
        })
    }

    override fun onClick(p0: View?) {
        navController.navigate(R.id.action_mainFragment_to_addNoteFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
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
                Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}