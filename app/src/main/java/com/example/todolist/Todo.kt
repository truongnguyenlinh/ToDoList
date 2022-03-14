package com.example.todolist

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "task") val task: String,
    @ColumnInfo(name = "is_checked") var is_checked: Boolean
) {
    override fun toString(): String = task
}