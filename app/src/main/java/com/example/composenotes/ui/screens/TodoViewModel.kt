package com.example.composenotes.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composenotes.data.Todo
import com.example.composenotes.data.TodosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

private const val TAG = "TodoViewModel"

@HiltViewModel
class TodoViewModel @Inject constructor(private val todosRepository: TodosRepository) :
    ViewModel() {
    val allTodos: StateFlow<List<Todo>> = todosRepository.getAllTodos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    var selectedTodo = MutableStateFlow(TodoUiState.todo)

    val newTodo = MutableStateFlow(TodoUiState.todo)

    fun onTodoSelected(id: Int) {
        viewModelScope.launch {
            try {

                todosRepository.getTodo(id).collect {
                    selectedTodo.update { todo ->
                        todo.copy(
                            id = it.id,
                            todo = it.todo,
                            isDone = it.isDone
                        )
                    }
                }
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun setSelectedTodo(todo: String) {
        selectedTodo.update { currentTodo -> currentTodo.copy(todo = todo) }
    }

    fun updateTodoDone(id: Int, isDone: Boolean) {
        viewModelScope.launch {
            todosRepository.updateIsDone(id, isDone)
        }
    }

    fun setNewTodo(todo: String) {
        newTodo.update { currentTodo -> currentTodo.copy(todo = todo) }
    }

    fun addTodo() {
        viewModelScope.launch {
            try {
                todosRepository.addTodo(newTodo.value)
                newTodo.value = TodoUiState.todo
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun updateTodo() {
        viewModelScope.launch {
            try {
                todosRepository.updateTodo(selectedTodo.value)
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }
    }


    fun deleteTodo(todo:Todo) {
        viewModelScope.launch {
            try {
                todosRepository.deleteTodo(todo)
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }
    }
}

object TodoUiState {
    val todo: Todo = Todo(todo = "", isDone = false)
}