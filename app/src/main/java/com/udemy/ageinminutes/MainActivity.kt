package com.udemy.ageinminutes

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.udemy.ageinminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        clickDatePicker()
    }

    private fun clickDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->
                Toast.makeText(
                    this,
                    "Year was $selectedYear, Month was ${selectedMonth + 1}, Day was $selectedDay",
                    Toast.LENGTH_SHORT
                ).show()

                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.txtDate.text = selectedDate

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val date = simpleDateFormat.parse(selectedDate)

                date?.let {
                    val selectedDateInMinutes = date.time / 60000
                    val currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))

                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        binding.txtMinutesDate.text = differenceInMinutes.toString()
                    }
                }
            }, year, month, day
        )

        binding.btnDatePicker.setOnClickListener {
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 86400000
            datePickerDialog.show()
        }
    }

}