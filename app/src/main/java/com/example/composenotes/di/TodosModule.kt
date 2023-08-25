package com.example.composenotes.di

import android.content.Context
import com.example.composenotes.data.TodosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TodosModule {

    @Provides
    @Singleton
    fun providesTodosDao(@ApplicationContext context: Context) =
        TodosDatabase.getDatabase(context).todosDao()
}