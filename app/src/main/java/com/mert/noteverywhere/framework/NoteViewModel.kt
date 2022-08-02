package com.mert.noteverywhere.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mert.core.data.Note
import com.mert.core.repository.NoteRepositoryImpl
import com.mert.core.usecase.AddNoteUseCase
import com.mert.core.usecase.GetAllNotesUseCase
import com.mert.core.usecase.GetNoteUseCase
import com.mert.core.usecase.RemoveNoteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val repositoryImpl = NoteRepositoryImpl(RoomNoteDataSource(application))

    val useCases = UseCases(
        AddNoteUseCase(repositoryImpl),
        GetAllNotesUseCase(repositoryImpl),
        GetNoteUseCase(repositoryImpl),
        RemoveNoteUseCase(repositoryImpl)
    )

    val saved = MutableLiveData<Boolean>()

    fun saveNote(note: Note){
        coroutineScope.launch {
            useCases.addNoteUseCase(note)
            saved.postValue(true)
        }
    }
}