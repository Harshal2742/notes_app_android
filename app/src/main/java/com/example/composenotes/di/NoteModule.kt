package com.example.composenotes.di

import android.content.Context
import com.example.composenotes.data.NotesDao
import com.example.composenotes.data.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun providesNoteDao(@ApplicationContext context: Context): NotesDao {
        return NotesDatabase.getDatabase(context).noteDao()
    }
}