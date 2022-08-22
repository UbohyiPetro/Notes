package com.example.notes.repository.api

interface NotesApi {
    suspend fun getAllNotes(): Boolean
}