package com.example.notes.repository.db.di

import android.content.Context
import androidx.room.Room
import com.example.notes.repository.db.NoteDao
import com.example.notes.repository.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomDBModule {

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.getNoteDao()

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext appContext: Context): NoteDatabase =
        Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            "notes_db.db"
        ).build()
}