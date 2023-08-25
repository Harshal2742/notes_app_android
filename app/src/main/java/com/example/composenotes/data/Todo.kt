package com.example.composenotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val todo: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean
)
