package com.example.offline_todo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.offline_todo.databinding.ActivityUpdateDataBinding

class UpdateData : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateDataBinding
    private val db: DatabaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateDataBinding.inflate(layoutInflater)

        val id = intent.getIntExtra("id", -1)
        val  todoData = db.getNoteById(id)
        binding.etTitle.setText(todoData.title)
        binding.etDes.setText(todoData.description)
        binding.txtDate.text = todoData.date
        binding.txtTime.text = todoData.time


        setContentView(binding.root)

    }
}