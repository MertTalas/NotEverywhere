package com.mert.core.repository

import com.mert.core.data.Note

interface NoteRepository {
    suspend fun addNote(note: Note)

    suspend fun getNote(id: Long): Note?

    suspend fun getAllNotes(): List<Note>

    suspend fun removeNote(note: Note)
}