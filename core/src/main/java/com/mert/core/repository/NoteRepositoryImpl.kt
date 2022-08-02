package com.mert.core.repository

import com.mert.core.data.Note

class NoteRepositoryImpl(private val noteRepository: NoteRepository) {

    suspend fun addNote(note: Note) = noteRepository.addNote(note)

    suspend fun getNote(id: Long) = noteRepository.getNote(id)

    suspend fun getAllNotes() = noteRepository.getAllNotes()

    suspend fun removeNote(note: Note) = noteRepository.removeNote(note)
}