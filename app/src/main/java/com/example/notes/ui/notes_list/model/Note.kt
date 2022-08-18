package com.example.notes.ui.notes_list.model

import com.example.notes.repository.db.model.NoteEntity
import java.io.Serializable

data class Note(
    val id: Int,
    val title: String = "New Note",
    val time: String = "16:49",
    val description: String = "Any description"
) : Serializable

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        time = time,
        description = description
    )
}