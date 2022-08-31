package com.mert.noteverywhere.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mert.core.data.Note
import com.mert.noteverywhere.R
import com.mert.noteverywhere.databinding.FragmentNoteBinding
import com.mert.noteverywhere.framework.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note.*

class NoteFragment : Fragment() {

    private var noteId = 0L
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
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.etNoteTitle.setBackgroundResource(R.drawable.button_selector_dark)
            binding.etNoteDesc.setBackgroundResource(R.drawable.button_selector_dark)
            binding.ivShare.setImageResource(R.drawable.ic_share_dark)
        } else {
            binding.etNoteTitle.setBackgroundResource(R.drawable.button_selector)
            binding.etNoteDesc.setBackgroundResource(R.drawable.button_selector)
            binding.ivShare.setImageResource(R.drawable.ic_share)
        }
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNoteById(noteId)
        }

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

        binding.fabDeleteNote.setOnClickListener {
            AlertDialog.Builder(context!!)
                .setTitle("Delete Note")
                .setMessage("Are you sure to delete this note ?")
                .setPositiveButton("Yes") { dialogInterface, i -> viewModel.deleteNote(currentNote) }
                .setNegativeButton("Cancel") { dialogInterface, i -> }
                .create()
                .show()
        }

        binding.ivShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                if (`package`.equals("com.whatsapp")){
                    setPackage("com.whatsapp")
                }
                putExtra(Intent.EXTRA_TEXT, binding.etNoteDesc.text.toString())
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        observeViewModel()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(tvTitle.windowToken, 0)
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(binding.root, "Note Created!", Snackbar.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(binding.fabSaveNote).popBackStack()
            } else {
                Snackbar.make(binding.root, "Something went wrong!!", Snackbar.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNote.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                currentNote = it
                binding.etNoteTitle.setText(it.title, TextView.BufferType.EDITABLE)
                binding.etNoteDesc.setText(it.content, TextView.BufferType.EDITABLE)
            }
        })

        viewModel.deleted.observe(viewLifecycleOwner, Observer {
            if (it) {
                Snackbar.make(binding.root, "Note Deleted!!", Snackbar.LENGTH_SHORT).show()
                Navigation.findNavController(binding.fabDeleteNote).popBackStack()
            } else {
                Snackbar.make(binding.root, "Something went wrong!!", Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}