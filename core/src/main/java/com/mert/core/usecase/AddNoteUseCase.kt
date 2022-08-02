package com.mert.core.usecase

import com.mert.core.data.Note
import com.mert.core.repository.NoteRepositoryImpl

class AddNoteUseCase(private val noteRepositoryImpl: NoteRepositoryImpl) {

    suspend operator fun invoke(note: Note) = noteRepositoryImpl.addNote(note)
}