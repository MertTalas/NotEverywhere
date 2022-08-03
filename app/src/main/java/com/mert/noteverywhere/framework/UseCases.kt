package com.mert.noteverywhere.framework

import com.mert.core.usecase.AddNoteUseCase
import com.mert.core.usecase.GetAllNotesUseCase
import com.mert.core.usecase.GetNoteUseCase
import com.mert.core.usecase.RemoveNoteUseCase

data class UseCases(
    val addNoteUseCase: AddNoteUseCase,
    val getAllNotesUseCase: GetAllNotesUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val removeNoteUseCase: RemoveNoteUseCase
)
