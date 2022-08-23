package com.example.notes.ui.note_details

import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.ui.notes_list.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.note_details_fragment.*
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class NoteDetailsFragment : Fragment(R.layout.note_details_fragment) {

    private val noteDetailsViewModel: NoteDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            noteDetailsViewModel.viewState.collect { noteDetailsViewState ->
                when {
                    noteDetailsViewState.isLoading -> loadingState()
                    !noteDetailsViewState.isLoading -> {
                        loadedState()
                        etTitle.setText(noteDetailsViewState.note.title)
                        etDescription.setText(noteDetailsViewState.note.description)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btUpdate -> {
                noteUpdate()
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_details, menu)
    }

    private fun noteUpdate() {
        val oldNote = noteDetailsViewModel.viewState.value.note
        val newNoteTitle = etTitle.text.toString()
        val newNoteDescription = etDescription.text.toString()
        if (oldNote.title != newNoteTitle || oldNote.description != newNoteDescription) {
            val updatedNote = Note(
                id = oldNote.id,
                title = newNoteTitle,
                description = newNoteDescription,
                time = DateFormat.format("hh:mm d:MM:yyyy", Date().time).toString()
            )
            noteDetailsViewModel.updateNote(updatedNote)
        }
    }


    private fun loadingState() {
        pbNoteDetails.isVisible = true
        etTitle.isVisible = false
        etDescription.isVisible = false
    }

    private fun loadedState() {
        pbNoteDetails.isVisible = false
        etTitle.isVisible = true
        etDescription.isVisible = true
    }
}