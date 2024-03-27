package com.example.exercises03

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.exercises03.databinding.ActivityEditBinding
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener


class EditActivity : AppCompatActivity(), ColorPickerDialogListener {
    private lateinit var binding : ActivityEditBinding
    private var selectedColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSaveButton()
        restoreHabit(savedInstanceState)
        setupColorPicker()
        setColorPreviewBackground()
    }


    private fun setupColorPicker() {
        binding.buttonChooseColor.setOnClickListener {
            showColorPickerDialog()
        }
    }

    private fun restoreHabit(savedInstanceState: Bundle?) {
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
        val colorPreview = binding.colorPreview
        colorPreview.setBackgroundColor(selectedColor)
    }

    private fun populateFields(habit: Habit) {
        binding.habitTitle.setText(habit.title)
        binding.habitDescription.setText(habit.description)

        val spinnerPriority = binding.spinnerPriority
        val priorityAdapter = spinnerPriority.adapter as ArrayAdapter<String>
        val priorityIndex = priorityAdapter.getPosition(habit.priority.priorityName)
        spinnerPriority.setSelection(priorityIndex)

        val radioGroupType = binding.radioGroupType
        val typeRadioButtonId = when (habit.type.typeName) {
            resources.getString(R.string.habitType1) -> R.id.radioButtonType1
            resources.getString(R.string.habitType2) -> R.id.radioButtonType2
            resources.getString(R.string.habitType3) -> R.id.radioButtonType3
            else -> -1
        }
        if (typeRadioButtonId != -1) {
            radioGroupType.check(typeRadioButtonId)
        }

        binding.amount.setText(habit.amount)

        val spinnerFrequency = binding.frequency
        val frequencyAdapter = spinnerFrequency.adapter as ArrayAdapter<String>
        val frequencyIndex = frequencyAdapter.getPosition(habit.frequency.freqName)
        spinnerFrequency.setSelection(frequencyIndex)
    }

    private fun setupSaveButton() {
        val saveButton = binding.saveButton
        saveButton.setOnClickListener {
            val habit = createHabitFromInputs()
            sendHabitResultBack(habit)
        }
    }

    private fun createHabitFromInputs(): Habit {
        val title = binding.habitTitle.text.toString()
        val description = binding.habitDescription.text.toString()
        val priority = binding.spinnerPriority.selectedItem.toString()
        val type = getTypeFromRadioGroup()
        val amount = binding.amount.text.toString()
        val frequency = binding.frequency.selectedItem.toString()
        val id = intent.getIntExtra("habitId", -1)
        return Habit(
            title = title,
            description = description,
            priority = HabitPriority.habitPriorityFromString(priority),
            type = HabitType.habitTypeFromString(type),
            amount = amount,
            frequency = HabitFrequency.habitFrequencyFromString(frequency),
            color = selectedColor,
            id = id
        )
    }


    private fun getTypeFromRadioGroup(): String {
        return when (binding.radioGroupType.checkedRadioButtonId) {
            // Обращение к R.string.habitType тоже сделать через viewBinding?
            binding.radioButtonType1.id -> resources.getString(R.string.habitType1)
            binding.radioButtonType2.id -> resources.getString(R.string.habitType2)
            binding.radioButtonType3.id -> resources.getString(R.string.habitType3)
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
        val colorPreview = binding.colorPreview
        colorPreview.setBackgroundColor(color)
    }

    override fun onDialogDismissed(dialogId: Int) {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedColor", selectedColor)
    }
}