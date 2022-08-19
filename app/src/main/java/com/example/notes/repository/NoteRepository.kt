package com.example.notes.repository

import com.example.notes.repository.db.NoteDao
import com.example.notes.repository.db.model.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun observeNotes(): Flow<List<NoteEntity>> = noteDao.observeAllNotes()

    suspend fun addNote(note: NoteEntity) = noteDao.addNote(note)

    suspend fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)

    suspend fun updateNote(note: NoteEntity) = noteDao.updateNote(note)

    suspend fun getNoteById(id: Int) = noteDao.getNoteById(id)

}