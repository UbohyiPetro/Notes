package com.example.notes.repository.db.model

import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.ui.notes_list.model.Note
import java.util.*

@Entity(
    tableName = "notes"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "title") val title: String = "New Note",
    @ColumnInfo(name = "time") val time: Long = Date().time,
    @ColumnInfo(name = "description") val description: String = ""
) {

}

fun NoteEntity.toNote(): Note {
    return Note(
        id = id ?: -1,
        title = title,
        time = DateFormat.format("hh:mm", Date(time)).toString(),
        description = description,
    )
}