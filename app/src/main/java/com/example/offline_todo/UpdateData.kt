package com.example.offline_todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.offline_todo.databinding.ActivityUpdateDataBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateData : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateDataBinding
    private val db: DatabaseHelper = DatabaseHelper(this)

    private var calendar: Calendar = Calendar.getInstance()

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


        binding.btnback.setOnClickListener {
            onBackPressed()
        }

        binding.btnUpdate.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDes.text.toString()
            val date = binding.txtDate.text.toString()
            val time = binding.txtTime.text.toString()

            val updatedTodo = TodoList(id, title, description, date, time)
            db.updateData(updatedTodo)
            finish()
            Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show()
        }

        setContentView(binding.root)
    }
}