package com.mert.noteverywhere.framework.di

import com.mert.noteverywhere.framework.ListViewModel
import com.mert.noteverywhere.framework.NoteViewModel
import dagger.Component

@Component(modules = [AppModule::class, RepositoryModule::class, UseCaseModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}