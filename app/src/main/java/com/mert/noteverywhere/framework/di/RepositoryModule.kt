package com.mert.noteverywhere.framework.di

import android.app.Application
import com.mert.core.repository.NoteRepositoryImpl
import com.mert.noteverywhere.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(application: Application) =
        NoteRepositoryImpl(RoomNoteDataSource(application))
}