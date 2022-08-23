package com.example.notes.ui.notes_list


import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.ui.notes_list.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.notes_list_fragment.*
import kotlinx.coroutines.launch
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


@AndroidEntryPoint
class NotesListFragment : Fragment(R.layout.notes_list_fragment) {

    private val notesViewModel: NotesViewModel by viewModels()
    private val notesListAdapter = NotesListAdapter()
    private var toastShowed = false


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
                    viewState.notes.isEmpty() && viewState.error == null -> errorState("No data")
                    viewState.notes.isNotEmpty() && viewState.error == null -> loadedState(
                        viewState.notes
                    )
                    viewState.error != null && viewState.notes.isEmpty() -> {
                        errorState("No internet connection")
                    }
                    viewState.error != null && viewState.notes.isNotEmpty() && !toastShowed -> {
                        loadedState(viewState.notes)
                        Toast.makeText(
                            requireContext(),
                            "No internet connection",
                            Toast.LENGTH_SHORT
                        ).show()
                        toastShowed = true
                    }
                    viewState.error != null && viewState.notes.isNotEmpty() && toastShowed -> loadedState(
                        viewState.notes
                    )
                }
            }
        }
    }

    private fun loadedState(notes: List<Note>) {
        notesListAdapter.submitList(notes)
        rvNotes.isVisible = true
        pbLoader.isVisible = false
        tvError.isVisible = false
    }

    private fun errorState(error: String) {
        pbLoader.isVisible = false
        rvNotes.isVisible = false
        tvError.apply {
            isVisible = true
            text = error
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
                return false
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

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
            }
        }
}