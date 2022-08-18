package com.example.notes.ui.note_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.ui.notes_list.model.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.note_details_fragment.*

@AndroidEntryPoint
class NoteDetailsFragment : Fragment(R.layout.note_details_fragment) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val note = requireArguments().getSerializable("note") as Note
        etTitle.setText(note.title)
        etDescription.setText(note.description)
    }
}