package com.example.composenotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodosDatabase : RoomDatabase() {
    abstract fun todosDao(): TodosDao

    companion object {

        @Volatile
        private var instance: TodosDatabase? = null

        fun getDatabase(context: Context): TodosDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, TodosDatabase::class.java, "todo_database")
                    .fallbackToDestructiveMigration().build().also {
                        instance = it
                    }
            }
        }
    }
}