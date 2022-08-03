package com.mert.noteverywhere.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.mert.noteverywhere.databinding.FragmentListBinding
import com.mert.noteverywhere.framework.ListViewModel

class ListFragment : Fragment(), ListActions {

    private lateinit var binding: FragmentListBinding
    private val notesListAdapter = ListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel

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

    private fun goToNoteDetails(id: Long = 0L){
        findNavController(binding.rvNotes).navigate(ListFragmentDirections.actionListFragmentToNoteFragment(id))
    }

    override fun onClickNote(id: Long) {
        goToNoteDetails(id)
    }
}