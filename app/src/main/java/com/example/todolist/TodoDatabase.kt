package com.example.todolist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object {
        private var instance: TodoDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): TodoDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext,
                    TodoDatabase::class.java,
                    "todo")
                    .allowMainThreadQueries()
                    .build()
            return instance!!
        }
    }
}