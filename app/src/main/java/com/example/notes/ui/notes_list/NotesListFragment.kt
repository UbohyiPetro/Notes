package com.example.notes.ui.notes_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.notes.R
import kotlinx.android.synthetic.main.notes_list_fragment.*

class NotesListFragment : Fragment(R.layout.notes_list_fragment) {

    private val notesListAdapter = NotesListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        rvNotes.apply {
            adapter = notesListAdapter
        }
    }


}