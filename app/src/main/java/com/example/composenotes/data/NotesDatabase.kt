package com.example.composenotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase() : RoomDatabase() {
    abstract fun noteDao(): NotesDao


    companion object {
        @Volatile
        private var instance: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, NotesDatabase::class.java, "note_database")
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }

}