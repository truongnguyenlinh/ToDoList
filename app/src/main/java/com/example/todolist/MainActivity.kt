package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemList = arrayListOf<Todo>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, itemList)

        val db by lazy {
            TodoDatabase.getInstance(this)
        }

        val editText = findViewById<EditText>(R.id.editText)
        val listView = findViewById<ListView>(R.id.listView)
        val addButton = findViewById<Button>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        listView.adapter = adapter

        db.todoDao().getAll().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                itemList.clear()
                itemList.addAll(it)
                it.forEach { i ->
                    val pos = adapter.getPosition(i)
                    if (i.is_checked) {
                        listView.setItemChecked(pos, true)
                    } else {
                        listView.setItemChecked(pos, false)
                    }
                }
                adapter.notifyDataSetChanged()
            } else {
                itemList.clear()
                adapter.notifyDataSetChanged()
            }
        })

        addButton.setOnClickListener {
            val input = editText.text.toString()
            if (input.isNotEmpty()) {
                db.todoDao().insert(Todo(0, editText.text.toString(), is_checked=false))
                itemList.add(Todo(0, editText.text.toString(), is_checked=false))
                adapter.notifyDataSetChanged()
                editText.text.clear()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Enter a valid task",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        deleteButton.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item)) {
                    db.todoDao().delete(itemList[item])
                    adapter.remove(itemList[item])
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }

        clearButton.setOnClickListener {
            itemList.clear()
            adapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { adapterView, view, pos, id ->
            itemList[pos].is_checked = !itemList[pos].is_checked
            db.todoDao().updateTodo(itemList[pos])
            adapter.notifyDataSetChanged()
        }
    }
}