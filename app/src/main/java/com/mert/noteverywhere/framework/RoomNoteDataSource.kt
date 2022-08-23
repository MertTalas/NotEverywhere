package com.mert.noteverywhere.framework

import android.content.Context
import com.mert.core.data.Note
import com.mert.core.repository.NoteRepository
import com.mert.noteverywhere.framework.db.NoteDatabase
import com.mert.noteverywhere.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteRepository {
    private val noteDao = NoteDatabase.getInstance(context).noteDao()

    override suspend fun addNote(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun getNote(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAllNotes() = noteDao.getAllNotesEntity().map { it.toNote() }

    override suspend fun removeNote(note: Note) =
        noteDao.removeNoteEntity(NoteEntity.fromNote(note))
}