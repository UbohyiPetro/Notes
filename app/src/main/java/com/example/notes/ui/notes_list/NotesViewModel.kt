package com.example.notes.ui.notes_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.helper.NetworkConnection
import com.example.notes.repository.NoteRepository
import com.example.notes.repository.db.model.NoteEntity
import com.example.notes.repository.db.model.toNote
import com.example.notes.ui.notes_list.model.Note
import com.example.notes.ui.notes_list.model.toNoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class NotesViewState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null,
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val networkConnection: NetworkConnection
) : ViewModel() {

    private val fetchNotesState: MutableStateFlow<Throwable?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            networkConnection.observeInternetConnection().collect {
                if (it) {
                    val result = noteRepository.fetchAllNotes()
                    if (result.isFailure) {
                        fetchNotesState.value = result.exceptionOrNull()
                    } else {
                        fetchNotesState.value = null
                    }
                }
                if (!it && fetchNotesState.value == null) {
                    fetchNotesState.value = Throwable("No internet connection")
                }
            }
        }
    }

    val notesViewState = combine(
        noteRepository.observeNotes(),
        fetchNotesState
    ) { notes, fetchException ->
        NotesViewState(
            isLoading = false,
            error = fetchException,
            notes = notes.map { noteEntity ->
                noteEntity.toNote()
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, NotesViewState())

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note.toNoteEntity())
        }
    }

    fun addNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val newNote = NoteEntity()
            noteRepository.addNote(newNote)
        }
    }

    fun networkConnection(): Flow<Boolean> = networkConnection.observeInternetConnection()
}
