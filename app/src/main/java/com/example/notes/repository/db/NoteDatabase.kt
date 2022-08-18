package com.example.notes.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.repository.db.model.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1,
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}