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
            //Create Date Picker Dialog
            val datePickerDialog = DatePickerDialog(this,{DatePicker,year: Int, monthOfYear: Int, dayOfMonth: Int ->
                //create new calendar instance to hot the selected date
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year,monthOfYear,dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formatDate = dateFormat.format(selectedDate.time)
                binding.txtDate.text = formatDate.toString()
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        binding.imgTime.setOnClickListener {
            //Create Time Picker Dialog
            val timePickerDialog = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                binding.txtTime.text = SimpleDateFormat("HH:mm").format(calendar.time)
            }
            TimePickerDialog(this, timePickerDialog, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        binding.faSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val description = binding.etDes.text.toString()
            val date = binding.txtDate.text.toString()
            val time = binding.txtTime.text.toString()
            Log.d("Date", "$title - $description - $date - $time")
            var  todo = TodoList(0, title, description, date, time)
            databaseHelper.insertData(todo)
            Toast.makeText(this, "To-Do Added Successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}