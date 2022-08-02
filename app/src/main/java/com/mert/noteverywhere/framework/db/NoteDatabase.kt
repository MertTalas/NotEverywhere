package com.mert.noteverywhere.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {

        private const val DATABASE_NAME = "note_db"

        private var instance: NoteDatabase? = null

        private fun create(context: Context): NoteDatabase =
            Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()


        // getInstance == Singleton
        fun getInstance(context: Context): NoteDatabase =
            (instance ?: create(context)).also { instance = it }
    }
}