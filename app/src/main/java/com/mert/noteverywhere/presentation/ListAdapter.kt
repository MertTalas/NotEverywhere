package com.mert.noteverywhere.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mert.core.data.Note
import com.mert.noteverywhere.R
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.util.*

class ListAdapter(var notes: ArrayList<Note>): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
    )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    inner class ListViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val noteLayout = view.noteLayout
        private val noteTitle = view.tvTitle
        private val noteDesc = view.tvDesc
        private val noteDate = view.tvDate

        fun bind(note: Note){
            noteTitle.text = note.title
            noteDesc.text = note.content

            val simpleDateFormat = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last Updated: ${simpleDateFormat.format(resultDate)}"
        }
    }

    fun updateList(newNotes: List<Note>){
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

}