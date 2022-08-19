package com.example.notes.ui.note_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.notes.R
import com.example.notes.ui.notes_list.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.note_details_fragment.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteDetailsFragment : Fragment(R.layout.note_details_fragment) {

    private val noteDetailsViewModel: NoteDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            noteDetailsViewModel.viewState.collect { noteDetailsViewState ->
                when {
                    noteDetailsViewState.isLoading -> {}
                    noteDetailsViewState.note != null -> {
                        etTitle.setText(noteDetailsViewState.note.title)
                        etDescription.setText(noteDetailsViewState.note.description)
                    }
                }
            }
        }

    }
}