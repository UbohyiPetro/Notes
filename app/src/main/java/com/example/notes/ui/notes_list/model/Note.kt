package com.example.notes.ui.notes_list.model

import com.example.notes.repository.db.model.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val id: Int,
    val title: String,
    val time: String,
    val description: String
)

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        time = SimpleDateFormat("hh:mm d:MM:yyyy", Locale.getDefault()).parse(time).time,
        description = description
    )
}