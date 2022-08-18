package com.example.notes.repository.db

import androidx.room.*
import com.example.notes.repository.db.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteEntity)

    @Query("SELECT * FROM notes")
    fun observeAllNotes(): Flow<List<NoteEntity>>

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)
}