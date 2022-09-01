package com.mert.noteverywhere.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mert.core.data.Note
import com.mert.noteverywhere.databinding.FragmentListBinding
import com.mert.noteverywhere.framework.ListViewModel

class ListFragment : Fragment(), ListActions {

    private lateinit var binding: FragmentListBinding
    private val notesListAdapter = ListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        binding.rvNotes.apply {
            adapter = notesListAdapter
        }

        binding.fabAddNote.setOnClickListener {
            goToNoteDetails()
        }

        onSwipeNote()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner, Observer { notesList ->
            binding.pbLoading.visibility = View.GONE
            binding.rvNotes.visibility = View.VISIBLE
            notesListAdapter.updateList(notesList.sortedByDescending {
                it.updateTime
            })
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun goToNoteDetails(id: Long = 0L) {
        findNavController(binding.rvNotes).navigate(
            ListFragmentDirections.actionListFragmentToNoteFragment(
                id
            )
        )
    }

    override fun onClickNote(id: Long) {
        goToNoteDetails(id)
    }

    override fun onSwipeNote() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                currentNote = notesListAdapter.get(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition
                notesListAdapter.removeAt(viewHolder.adapterPosition)
                viewModel.deleteNotes(currentNote)

                Snackbar.make(
                    binding.rvNotes, "Note " + currentNote.title + " Deleted!!",
                    Snackbar.LENGTH_LONG
                ).setAction("Undo", View.OnClickListener {
                    notesListAdapter.add(position, currentNote)
                    viewModel.saveNotes(currentNote)
                    notesListAdapter.notifyItemInserted(position)
                }).show()
            }
        }).attachToRecyclerView(binding.rvNotes)
    }
}