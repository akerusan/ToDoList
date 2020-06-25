package com.akerusan.todolist.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.akerusan.todolist.R
import com.akerusan.todolist.db.Note
import com.akerusan.todolist.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var navController: NavController
    private var editMode = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        navController = Navigation.findNavController(view)

        if (arguments != null) { editMode = true }
        if (editMode) {
            requireActivity().title = "Edit Note"
            add_title.setText(requireArguments().getString("title"))
            add_description.setText(requireArguments().getString("description"))
            var df = SimpleDateFormat("yyyy-mm-dd")
            val date: Date = df.parse(requireArguments().getString("date")!!)!!
            add_date.updateDate(date.year, date.month, date.day)
        } else {
            requireActivity().title = "Add Note"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
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
        val date = "${add_date.year}-${add_date.month+1}-${add_date.dayOfMonth}"

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(context, "Please insert a title or description", Toast.LENGTH_SHORT).show()
            return
        } else {
            if(!editMode){
                noteViewModel.insert(
                    Note(
                        title,
                        description,
                        date
                    )
                )

                Toast.makeText(context, "Note Saved", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                val updatedNote =
                    Note(title, description, date)
                updatedNote.id = requireArguments().getInt("id", -1)
                noteViewModel.update(updatedNote)
                Toast.makeText(context, "Note Edited", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }
        }
    }
}