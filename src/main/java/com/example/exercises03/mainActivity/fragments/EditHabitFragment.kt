package com.example.exercises03.mainActivity.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.exercises03.MAIN
import com.example.exercises03.R
import com.example.exercises03.databinding.EditFragmentLayoutBinding
import com.example.exercises03.habitModel.Habit
import com.example.exercises03.habitModel.HabitFrequency
import com.example.exercises03.habitModel.HabitPriority
import com.example.exercises03.habitModel.HabitType
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class EditHabitFragment : Fragment(), ColorPickerDialogListener {

    private lateinit var binding: EditFragmentLayoutBinding
    private var selectedColor = Color.BLACK
    private var actionCode: Int = -1
    private var id = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val habitToEdit = arguments?.getSerializable("habit") as? Habit
        actionCode = arguments?.getInt("actionCode") ?: -1
        id = arguments?.getInt("habitIndex") as Int

        habitToEdit?.let {
            populateFields(it)
            selectedColor = it.color
        }
    }

    private fun setColorPreviewBackground() {
        binding.colorPreview.setBackgroundColor(selectedColor)
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
        val id = id
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
        val bundle = Bundle().apply {
            putSerializable("habit", habit)
            putInt("actionCode", actionCode)
            putInt("habitIndex", id)
            putBundle("habitBundle", this)
        }
        MAIN.navController.navigate(R.id.action_editHabitFragment_to_habitListFragment, bundle)
    }

    private fun showColorPickerDialog() {
        val colorPickerDialog = ColorPickerDialog.newBuilder()
            .setDialogType(ColorPickerDialog.TYPE_PRESETS)
            .setAllowCustom(true)
            .setShowAlphaSlider(true)
            .setColor(selectedColor)
            .setPresets(intArrayOf(Color.RED, Color.GREEN, Color.BLUE))
            .create()

        colorPickerDialog.show(childFragmentManager, "color_picker_dialog")
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
        selectedColor = color
        setColorPreviewBackground()
    }

    override fun onDialogDismissed(dialogId: Int) {}


}
