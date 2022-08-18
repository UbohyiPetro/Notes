package com.example.notes.repository.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.ui.notes_list.model.Note

@Entity(
    tableName = "notes"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "title") val title: String = "New Note",
    @ColumnInfo(name = "time") val time: String = "16:49",
    @ColumnInfo(name = "description") val description: String = "Any description"
) {

}

fun NoteEntity.toNote(): Note {
    return Note(
        id = id ?: -1,
        title = title,
        time = time,
        description = description,
    )
}