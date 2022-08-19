package com.example.notes.ui.notes_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.notes_list_fragment.*
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class NotesListFragment : Fragment(R.layout.notes_list_fragment) {

    private val notesViewModel: NotesViewModel by viewModels()
    private val notesListAdapter = NotesListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeNotes()
        setupItemTouchHelper()
        setOnClickListenerForFloatingActionButton()
    }

    private fun observeNotes() {
        lifecycleScope.launch {
            notesViewModel.notesViewState.collect { viewState ->
                when {
                    viewState.isLoading -> loadingState()
                    viewState.notes.isEmpty() -> noDataState()
                    viewState.notes.isNotEmpty() -> {
                        loadedState()
                        notesListAdapter.submitList(viewState.notes)
                    }
                }
            }
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

    private fun loadedState() {
        rvNotes.isVisible = true
        pbLoader.isVisible = false
        tvError.isVisible = false
    }

    private fun noDataState() {
        pbLoader.isVisible = false
        rvNotes.isVisible = false
        tvError.apply {
            isVisible = true
            text = "No Data"
        }
    }

    private fun loadingState() {
        rvNotes.isVisible = false
        tvError.isVisible = false
        pbLoader.isVisible = true
    }

    private fun removeNote(position: Int) {
        notesViewModel.deleteNote(notesViewModel.notesViewState.value.notes[position])
    }

    private fun setupRecyclerView() {
        rvNotes.apply {
            adapter = notesListAdapter
        }
    }

    private fun addNote() {
        notesViewModel.addNote()
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(rvNotes)
    }

    private fun setOnClickListenerForFloatingActionButton() {
        fbAddNote.setOnClickListener {
            addNote()
        }
    }
}