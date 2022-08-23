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

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
            .appModule(AppModule((getApplication())))
            .build()
            .inject(this)
    }

    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()
    val deleted = MutableLiveData<Boolean>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNoteUseCase(note)
            saved.postValue(true)
        }
    }

    fun getNoteById(id: Long) {
        coroutineScope.launch {
            val note = useCases.getNoteUseCase(id)
            currentNote.postValue(note)
        }
    }

    fun deleteNote(note: Note) {
        coroutineScope.launch {
            useCases.removeNoteUseCase(note)
            deleted.postValue(true)
        }
    }
}