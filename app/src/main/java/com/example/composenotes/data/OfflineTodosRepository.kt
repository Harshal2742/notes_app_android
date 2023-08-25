package com.example.composenotes.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineTodosRepository @Inject constructor(private val todosDao: TodosDao) : TodosRepository {
    override suspend fun addTodo(todo: Todo) = todosDao.addTodo(todo)

    override suspend fun updateTodo(todo: Todo) = todosDao.updateTodo(todo)

    override suspend fun deleteTodo(todo: Todo) = todosDao.deleteTodo(todo)

    override fun getAllTodos(): Flow<List<Todo>> = todosDao.getAllTodos()

    override fun getTodo(id: Int): Flow<Todo> = todosDao.getTodo(id)

    override suspend fun updateIsDone(id: Int, isDone: Boolean) = todosDao.updateIsDone(id, isDone)
}