package com.mert.noteverywhere.framework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun addNoteEntity(noteEntity: NoteEntity)

    @Query("SELECT * FROM note_item WHERE id=:id")
    suspend fun getNoteEntity(id: Long): NoteEntity?

    @Query("SELECT * FROM note_item")
    suspend fun getAllNotesEntity(): List<NoteEntity>

    @Delete
    suspend fun removeNoteEntity(noteEntity: NoteEntity)
}