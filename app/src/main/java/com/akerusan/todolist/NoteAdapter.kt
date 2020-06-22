package com.akerusan.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(private val noteList: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(item)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = noteList[position]

        holder.priority.text = currentItem.priority.toString()
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
    }

    override fun getItemCount() = noteList.size

    class NoteViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val priority = item.text_view_priority
        val title = item.text_view_title
        val description = item.text_view_description
    }
}