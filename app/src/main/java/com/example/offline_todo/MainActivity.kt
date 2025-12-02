package com.example.offline_todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.offline_todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: DatabaseHelper
    lateinit var adapter: ToDoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        db = DatabaseHelper(this)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.addTodo.setOnClickListener {
            startActivity(Intent(this, AddToDo::class.java))
        }

        var layoutManager = LinearLayoutManager(this)
        binding.rvTodo.layoutManager = layoutManager


        adapter = ToDoAdapter(db.getAllData(), this)
        binding.rvTodo.adapter = adapter


    }
    override fun onResume() {
        super.onResume()
        adapter.refreshData(db.getAllData())

    }

}