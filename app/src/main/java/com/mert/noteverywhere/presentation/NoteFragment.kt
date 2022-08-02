package com.mert.noteverywhere.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mert.core.data.Note
import com.mert.noteverywhere.databinding.FragmentNoteBinding
import com.mert.noteverywhere.framework.NoteViewModel

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.fabSaveNote.setOnClickListener {
            if (binding.etNoteTitle.text.toString() != "" || binding.etNoteDesc.text.toString() != "") {
                val currentTime = System.currentTimeMillis()
                currentNote.title = binding.etNoteTitle.text.toString()
                currentNote.content = binding.etNoteDesc.text.toString()
                currentNote.updateTime = currentTime

                if (currentNote.id == 0L) { //if it is new note
                    currentNote.creationTime = currentTime
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it){
                Snackbar.make(binding.root,"Note Created!",Snackbar.LENGTH_SHORT).show()
                Navigation.findNavController(binding.fabSaveNote).popBackStack()
            }else{
                Snackbar.make(binding.root,"Something went wrong!!",Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}