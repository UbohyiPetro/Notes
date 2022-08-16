package com.example.notes.ui.notes_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import kotlinx.android.synthetic.main.note_item.view.*

class NotesListAdapter : RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {

    val notes: List<Note> = listOf(
        Note("Do Something", "16:49", "Any descriptionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
    )

    class NotesListViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NotesListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
        val note = notes[position]
        holder.itemView.apply {
            tvTitle.text = note.title
            tvTime.text = note.time
            tvDescription.text = note.description
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}

data class Note(
    val title: String,
    val time: String,
    val description: String
)