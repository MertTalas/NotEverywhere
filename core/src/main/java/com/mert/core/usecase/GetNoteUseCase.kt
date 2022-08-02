package com.mert.core.usecase

import com.mert.core.data.Note
import com.mert.core.repository.NoteRepositoryImpl

class GetNoteUseCase(private val noteRepositoryImpl: NoteRepositoryImpl) {

    suspend operator fun invoke(id: Long) = noteRepositoryImpl.getNote(id)
}