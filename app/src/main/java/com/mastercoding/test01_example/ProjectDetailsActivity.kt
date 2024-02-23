package com.mastercoding.test01_example

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class ProjectDetailsActivity : AppCompatActivity() {

    private lateinit var editTextId: EditText
    private lateinit var editTextName: EditText
    private lateinit var spinnerDuration: Spinner
    private lateinit var radioGroupType: RadioGroup
    private lateinit var checkBoxSponsor1: CheckBox
    private lateinit var checkBoxSponsor2: CheckBox
    private lateinit var checkBoxSponsor3: CheckBox
    private lateinit var editTextSelectDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var editTextCost: EditText
    private lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_details)

        initViewReferences()
        setupDatePicker()
        setupTimePicker()
        setupNextButton()
    }

    private fun initViewReferences() {
        editTextId = findViewById(R.id.editTextId)
        editTextName = findViewById(R.id.editTextName)
        spinnerDuration = findViewById(R.id.spinnerDuration)
        radioGroupType = findViewById(R.id.radioGroupType)
        checkBoxSponsor1 = findViewById(R.id.checkBoxSponsor1)
        checkBoxSponsor2 = findViewById(R.id.checkBoxSponsor2)
        checkBoxSponsor3 = findViewById(R.id.checkBoxSponsor3)
        editTextSelectDate = findViewById(R.id.editTextSelectDate)
        editTextTime = findViewById(R.id.editTextTime)
        editTextCost = findViewById(R.id.editTextCost)
        buttonNext = findViewById(R.id.buttonNext)

        setupSpinnerWithPrompt()
    }

    private fun setupSpinnerWithPrompt() {
        val durations = resources.getStringArray(R.array.duration).toMutableList()
        durations.add(0, "Select Duration") // Adding prompt as the first item

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, durations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDuration.adapter = adapter
    }


    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()

        // callback for date picker
        val datePickerDialog = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            editTextSelectDate.setText(format.format(calendar.time))
        }

        // when user clicks on the date field, show the date picker dialog
        editTextSelectDate.setOnClickListener {
            DatePickerDialog(this, datePickerDialog, // this will be called when user selects a date
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show() // initially, the current date will be shown
        }
    }

    private fun setupTimePicker() {
        editTextTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
                editTextTime.setText(timeFormat.format(calendar.time))
            }

            TimePickerDialog(this, timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }
    }


    private fun setupNextButton() {
        buttonNext.setOnClickListener {
            if (validateInput()) {
                val intent = Intent(this, ResourcesDetailsActivity::class.java).apply {
                    putExtra("projectName", editTextName.text.toString())
                    putExtra("projectCost", editTextCost.text.toString())
                    putExtra("projectSponsors", getSelectedSponsors())
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please ensure all fields are correctly filled.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getSelectedSponsors(): String {
        val selectedSponsors = mutableListOf<String>()
        if (checkBoxSponsor1.isChecked) selectedSponsors.add("Sponsor01")
        if (checkBoxSponsor2.isChecked) selectedSponsors.add("Sponsor02")
        if (checkBoxSponsor3.isChecked) selectedSponsors.add("Sponsor03")
        return selectedSponsors.joinToString(", ")
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (editTextId.text.isEmpty() || editTextId.text.length != 3) {
            editTextId.error = "Project ID must be 3 digits long"
            isValid = false
        }

        if (editTextName.text.isEmpty()) {
            editTextName.error = "Project Name cannot be empty"
            isValid = false
        }

        if (editTextCost.text.isEmpty()) {
            editTextCost.error = "Project Cost cannot be empty"
            isValid = false
        }

        if (spinnerDuration.selectedItemPosition == 0) {
            Toast.makeText(this, "Select Project Duration", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (radioGroupType.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Select Project Type", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (editTextSelectDate.text.isEmpty()) {
            editTextSelectDate.error = "Select Project Start Date"
            isValid = false
        }

        if (editTextTime.text.isEmpty()) {
            editTextTime.error = "Select Project Start Time"
            isValid = false
        }

        if (editTextCost.text.isEmpty()) {
            editTextCost.error = "Project Cost cannot be empty"
            isValid = false
        }

        if (!checkBoxSponsor1.isChecked && !checkBoxSponsor2.isChecked && !checkBoxSponsor3.isChecked) {
            Toast.makeText(this, "Select at least one sponsor", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }
}




