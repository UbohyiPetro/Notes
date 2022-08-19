package com.example.notes.ui.note_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.notes.repository.NoteRepository
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val noteId = savedStateHandle.get<Int>("id")


}