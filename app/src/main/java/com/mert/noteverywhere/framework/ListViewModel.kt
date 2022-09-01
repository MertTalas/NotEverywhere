package com.mert.noteverywhere.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mert.core.data.Note
import com.mert.noteverywhere.framework.di.AppModule
import com.mert.noteverywhere.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule((getApplication())))
            .build()
            .inject(this)
    }

    val notes = MutableLiveData<List<Note>>()
    val deletedNotes = MutableLiveData<Boolean>()
    val savedNotes = MutableLiveData<Boolean>()

    fun getNotes() {
        coroutineScope.launch {
            val noteList = useCases.getAllNotesUseCase()
            notes.postValue(noteList)
        }
    }

    fun deleteNotes(note: Note) {
        coroutineScope.launch {
            useCases.removeNoteUseCase(note)
            deletedNotes.postValue(true)
        }
    }

    fun saveNotes(note: Note) {
        coroutineScope.launch {
            useCases.addNoteUseCase(note)
            savedNotes.postValue(true)
        }
    }
}