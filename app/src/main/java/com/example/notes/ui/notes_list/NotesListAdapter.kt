package com.example.notes.ui.notes_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import kotlinx.android.synthetic.main.note_item.view.*

class NotesListAdapter() : RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder>() {

    private var notesList: MutableList<Note> = mutableListOf()

    class NotesListViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NotesListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesListViewHolder, position: Int) {
        val note = notesList[position]
        holder.itemView.apply {
            setOnClickListener {
                findNavController().navigate(
                    NotesListFragmentDirections.actionNotesListFragmentToNoteDetailsFragment(note)
                )
            }
            tvTitle.text = note.title
            tvTime.text = note.time
            tvDescription.text = note.description
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(notes: MutableList<Note>) {
        notesList = notes
        notifyDataSetChanged()
    }


}
