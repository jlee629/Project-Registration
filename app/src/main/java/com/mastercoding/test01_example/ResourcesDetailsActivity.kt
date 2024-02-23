package com.mastercoding.test01_example

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResourcesDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources_details)

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextEmail: EditText = findViewById(R.id.editTextTextEmail)
        val editTextHourlyRate: EditText = findViewById(R.id.editTextHourlyRate)
        val editTextNumberOfHours: EditText = findViewById(R.id.editTextNumberOfHours)
        val buttonBack: Button = findViewById(R.id.buttonBack)
        val buttonSubmit: Button = findViewById(R.id.buttonSubmit)

        buttonBack.setOnClickListener {
            finish()
        }

        buttonSubmit.setOnClickListener {
            if (validateInput(editTextName, editTextEmail, editTextHourlyRate, editTextNumberOfHours)) {
                val hourlyRate = editTextHourlyRate.text.toString().toDouble()
                val numberOfHours = editTextNumberOfHours.text.toString().toInt()
                val earnings = hourlyRate * numberOfHours

                val projectName = intent.getStringExtra("projectName") ?: "Unknown Project"
                val projectSponsor = intent.getStringExtra("projectSponsor") ?: "Unknown Sponsor"
                val projectCost = intent.getDoubleExtra("projectCost", 0.0)
                val message = "Project: $projectName, Sponsor: $projectSponsor, Cost: $$projectCost, Employee Earnings: $$earnings"
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateInput(editTextName: EditText, editTextEmail: EditText, editTextHourlyRate: EditText, editTextNumberOfHours: EditText): Boolean {
        var isValid = true

        if (editTextName.text.isBlank()) {
            editTextName.error = "Employee Name cannot be empty"
            isValid = false
        }

        if (editTextEmail.text.isBlank()) {
            editTextEmail.error = "Email Id cannot be empty"
            isValid = false
        }

        val hourlyRate = editTextHourlyRate.text.toString().toDoubleOrNull()
        if (hourlyRate == null || hourlyRate < 30.0 || hourlyRate > 60.0) {
            editTextHourlyRate.error = "Hourly Rate must be between $30 and $60"
            isValid = false
        }

        val numberOfHours = editTextNumberOfHours.text.toString().toIntOrNull()
        if (numberOfHours == null || numberOfHours < 100 || numberOfHours > 200) {
            editTextNumberOfHours.error = "Number of Hours must be between 100 and 200"
            isValid = false
        }

        return isValid
    }
}
