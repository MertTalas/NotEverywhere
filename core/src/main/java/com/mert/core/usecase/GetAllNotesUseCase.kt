package com.mert.core.usecase

import com.mert.core.repository.NoteRepositoryImpl

class GetAllNotesUseCase(private val noteRepositoryImpl: NoteRepositoryImpl) {

    suspend operator fun invoke() = noteRepositoryImpl.getAllNotes()
}