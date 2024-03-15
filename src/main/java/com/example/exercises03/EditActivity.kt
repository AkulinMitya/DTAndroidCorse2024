package com.example.exercises03

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener


class EditActivity : AppCompatActivity(), ColorPickerDialogListener {

    private var selectedColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupSaveButton()
        restoreInstanceState(savedInstanceState)
        setupColorPicker()
        setColorPreviewBackground()
    }


    private fun setupColorPicker() {
        findViewById<Button>(R.id.buttonChooseColor).setOnClickListener {
            showColorPickerDialog()
        }
    }

    private fun restoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            selectedColor = it.getInt("selectedColor", Color.BLACK)
        }

        val habitToEdit = intent.getSerializableExtra("habitToEdit") as? Habit
        habitToEdit?.let {
            populateFields(it)
            selectedColor = it.color
        }
    }

    private fun setColorPreviewBackground() {
        val colorPreview = findViewById<View>(R.id.colorPreview)
        colorPreview.setBackgroundColor(selectedColor)
    }

    private fun populateFields(habit: Habit) {
        findViewById<EditText>(R.id.habitTitle).setText(habit.title)
        findViewById<EditText>(R.id.habitDescription).setText(habit.description)

        val spinnerPriority = findViewById<Spinner>(R.id.spinnerPriority)
        val priorityAdapter = spinnerPriority.adapter as ArrayAdapter<String>
        val priorityIndex = priorityAdapter.getPosition(habit.priority)
        spinnerPriority.setSelection(priorityIndex)

        val radioGroupType = findViewById<RadioGroup>(R.id.radioGroupType)
        val typeRadioButtonId = when (habit.type) {
            resources.getString(R.string.habitType1) -> R.id.radioButtonType1
            resources.getString(R.string.habitType2) -> R.id.radioButtonType2
            resources.getString(R.string.habitType3) -> R.id.radioButtonType3
            else -> -1
        }
        if (typeRadioButtonId != -1) {
            radioGroupType.check(typeRadioButtonId)
        }

        findViewById<EditText>(R.id.amount).setText(habit.amount)

        val spinnerFrequency = findViewById<Spinner>(R.id.frequency)
        val frequencyAdapter = spinnerFrequency.adapter as ArrayAdapter<String>
        val frequencyIndex = frequencyAdapter.getPosition(habit.frequency)
        spinnerFrequency.setSelection(frequencyIndex)
    }

    private fun setupSaveButton() {
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val habit = createHabitFromInputs()
            sendHabitResultBack(habit)
        }
    }

    private fun createHabitFromInputs(): Habit {
        val title = findViewById<EditText>(R.id.habitTitle).text.toString()
        val description = findViewById<EditText>(R.id.habitDescription).text.toString()
        val priority = findViewById<Spinner>(R.id.spinnerPriority).selectedItem.toString()
        val type = getTypeFromRadioGroup()
        val amount = findViewById<EditText>(R.id.amount).text.toString()
        val frequency = findViewById<Spinner>(R.id.frequency).selectedItem.toString()

        val id = intent.getIntExtra("habitId", -1) as Int
        return Habit(
            title = title,
            description = description,
            priority = priority,
            type = type,
            amount = amount,
            frequency = frequency,
            color = selectedColor,
            id = id
        )
    }

    private fun getTypeFromRadioGroup(): String {
        return when (findViewById<RadioGroup>(R.id.radioGroupType).checkedRadioButtonId) {
            R.id.radioButtonType1 -> resources.getString(R.string.habitType1)
            R.id.radioButtonType2 -> resources.getString(R.string.habitType2)
            R.id.radioButtonType3 -> resources.getString(R.string.habitType3)
            else -> ""
        }
    }

    private fun sendHabitResultBack(habit: Habit) {
        val resultIntent = Intent()
        resultIntent.putExtra("habit", habit)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun showColorPickerDialog() {
        val colorPickerDialog = ColorPickerDialog.newBuilder()
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setAllowCustom(true)
            .setShowAlphaSlider(true)
            .setDialogId(DIALOG_COLOR_PICKER_ID)
            .setColor(selectedColor)
            .setPresets(intArrayOf(Color.RED, Color.GREEN, Color.BLUE))
            .create()

        colorPickerDialog.show(supportFragmentManager, "color_picker_dialog")
    }


    companion object {
        private const val DIALOG_COLOR_PICKER_ID = 0
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        selectedColor = color
        val colorPreview = findViewById<View>(R.id.colorPreview)
        colorPreview.setBackgroundColor(color)
    }

    override fun onDialogDismissed(dialogId: Int) {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedColor", selectedColor)
    }
}