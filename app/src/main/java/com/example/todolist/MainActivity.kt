package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemList = arrayListOf<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, itemList)

        val editText = findViewById<EditText>(R.id.editText)
        val listView = findViewById<ListView>(R.id.listView)
        val addButton = findViewById<Button>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val clearButton = findViewById<Button>(R.id.clearButton)

        listView.adapter = adapter
        addButton.setOnClickListener {
            itemList.add(editText.text.toString())
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }

        deleteButton.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item)) {
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
    }
}