package com.akerusan.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akerusan.todolist.db.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.title == newItem.title &&
                       oldItem.description == newItem.description &&
                       oldItem.date == newItem.date
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(item)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.date.text = currentItem.date
    }

    fun getNoteAt(position: Int) : Note {
        return getItem(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class NoteViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title = item.text_view_title
        val description = item.text_view_description
        val date = item.text_view_date

        init {
            item.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener!!.onItemClick(getItem(position))
                }
            }
        }
    }
}