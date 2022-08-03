package com.mert.noteverywhere.framework.di

import com.mert.core.repository.NoteRepositoryImpl
import com.mert.core.usecase.AddNoteUseCase
import com.mert.core.usecase.GetAllNotesUseCase
import com.mert.core.usecase.GetNoteUseCase
import com.mert.core.usecase.RemoveNoteUseCase
import com.mert.noteverywhere.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getUseCases(repositoryImpl: NoteRepositoryImpl) = UseCases(
        AddNoteUseCase(repositoryImpl),
        GetAllNotesUseCase(repositoryImpl),
        GetNoteUseCase(repositoryImpl),
        RemoveNoteUseCase(repositoryImpl)
    )
}