package com.example.composenotes.di

import com.example.composenotes.data.NotesRepository
import com.example.composenotes.data.OfflineNotesRepository
import com.example.composenotes.data.OfflineTodosRepository
import com.example.composenotes.data.TodosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNotesRepository(offlineNotesRepository: OfflineNotesRepository): NotesRepository

    @Binds
    @Singleton
    abstract fun bindTodosRepository(offlineTodosRepository: OfflineTodosRepository): TodosRepository
}