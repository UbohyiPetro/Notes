package com.example.notes.ui.notes_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import kotlinx.android.synthetic.main.notes_list_fragment.*
import java.util.*

class NotesListFragment : Fragment(R.layout.notes_list_fragment) {

    private val notesViewModel: NotesViewModel by viewModels()
    private val notesListAdapter = NotesListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupItemTouchHelper()
        notesListAdapter.setList(notesViewModel.notes)
        fbAddNote.setOnClickListener {
            val newNote = Note()
            addNote(newNote)
            notesListAdapter.notifyItemInserted(0)
        }
    }

    private val itemTouchHelper: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
            ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition
                Collections.swap(notesViewModel.notes, startPosition, endPosition)
                notesListAdapter.notifyItemMoved(startPosition, endPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        removeNote(position)
                    }
                    ItemTouchHelper.RIGHT -> {
                        removeNote(position)
                    }
                }
            }
        }

    private fun removeNote(position: Int) {
        notesViewModel.notes.removeAt(position)
        notesListAdapter.notifyItemRemoved(position)
    }

    private fun setupRecyclerView() {
        rvNotes.apply {
            adapter = notesListAdapter
        }
    }

    private fun addNote(newNote: Note) {
        notesViewModel.notes.add(0, newNote)
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(rvNotes)
    }

}