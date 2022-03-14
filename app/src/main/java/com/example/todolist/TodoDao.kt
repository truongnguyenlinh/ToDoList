package com.example.todolist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.Delete
import androidx.lifecycle.LiveData

@Dao
interface TodoDao {

    @Query("Select * from todo")
    fun getAll(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE is_checked is 'True'")
    fun findByChecked(): List<Todo>

    @Insert
    fun insert(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Update
    fun updateTodo(vararg todos: Todo)


}