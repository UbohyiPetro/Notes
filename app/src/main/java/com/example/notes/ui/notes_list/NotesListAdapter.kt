package com.example.notes.ui.notes_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.ui.notes_list.model.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesListAdapter : ListAdapter<Note, NotesListAdapter.NotesViewHolder>(
    object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
) {
    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = currentList[position]
        holder.itemView.apply {
            setOnClickListener {
                findNavController().navigate(
                    NotesListFragmentDirections.actionNotesListFragmentToNoteDetailsFragment(note.id)
                )
            }
            tvTitle.text = note.title
            tvTime.text = note.time
            tvDescription.text = note.description
        }
    }
}
