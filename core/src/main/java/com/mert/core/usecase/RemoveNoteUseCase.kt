package com.mert.core.usecase

import com.mert.core.data.Note
import com.mert.core.repository.NoteRepositoryImpl

class RemoveNoteUseCase(private val noteRepositoryImpl: NoteRepositoryImpl) {

    suspend operator fun invoke(note: Note) = noteRepositoryImpl.removeNote(note)
}