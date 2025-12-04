package com.example.offline_todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.offline_todo.Database.DatabaseHelper
import com.example.offline_todo.Model.TodoList
import com.example.offline_todo.databinding.ActivityAddToDoBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddToDo : AppCompatActivity() {
    lateinit var binding: ActivityAddToDoBinding
    private var calendar: Calendar = Calendar.getInstance()
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddToDoBinding.inflate(layoutInflater)
        databaseHelper = DatabaseHelper(this)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnback.setOnClickListener {
            onBackPressed()
        }

        binding.imgDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = Calendar.getInstance().apply {
                        set(year, monthOfYear, dayOfMonth)
                    }
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    binding.txtDate.text = dateFormat.format(selectedDate.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.imgTime.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                binding.txtTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.faSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDes.text.toString().trim()
            val date = binding.txtDate.text.toString().trim()
            val time = binding.txtTime.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (date.isEmpty()) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("Date", "$title - $description - $date - $time")
            val todo = TodoList(0, title, description, date, time)
            databaseHelper.insertData(todo)

            Toast.makeText(this, "To-Do Added Successfully", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}
