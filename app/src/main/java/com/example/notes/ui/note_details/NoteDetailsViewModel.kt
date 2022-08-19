package com.example.notes.ui.note_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.repository.NoteRepository
import com.example.notes.repository.db.model.toNote
import com.example.notes.ui.notes_list.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteDetailsViewState(
    val note: Note? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId = savedStateHandle.get<Int>("id")
    private val _viewState: MutableStateFlow<NoteDetailsViewState> =
        MutableStateFlow(NoteDetailsViewState())
    val viewState: StateFlow<NoteDetailsViewState>
        get() = _viewState

    init {
        viewModelScope.launch {
            _viewState.value = NoteDetailsViewState(
                isLoading = false,
                note = noteRepository.getNoteById(noteId!!).toNote()
            )
        }
    }

}