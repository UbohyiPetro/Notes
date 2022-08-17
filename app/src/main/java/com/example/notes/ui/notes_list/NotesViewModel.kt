package com.example.notes.ui.notes_list

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

class NotesViewModel : ViewModel() {

    val notes: MutableList<Note> = mutableListOf(
        Note(
            "Do Something",
            "16:49",
            "Any descriptionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
        ),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
        Note("Do Something", "16:49", "Any description"),
    )
}

data class Note(
    val title: String = "New Note",
    val time: String = "16:49",
    val description: String = ""
) : Serializable