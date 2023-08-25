package com.example.composenotes.data

import kotlinx.coroutines.flow.Flow

interface TodosRepository {

    suspend fun addTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getAllTodos(): Flow<List<Todo>>

    fun getTodo(id: Int): Flow<Todo>

   suspend fun updateIsDone(id:Int,isDone:Boolean)
}