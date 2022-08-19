package com.example.notes.ui.notes_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.repository.NoteRepository
import com.example.notes.repository.db.model.NoteEntity
import com.example.notes.repository.db.model.toNote
import com.example.notes.ui.notes_list.model.Note
import com.example.notes.ui.notes_list.model.toNoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotesViewState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val notesViewState: StateFlow<NotesViewState> =
        noteRepository.observeNotes().map { notesEntity ->
            NotesViewState(
                notes = notesEntity.map { noteEntity ->
                    noteEntity.toNote()
                }, isLoading = false
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

}
