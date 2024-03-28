package com.example.exercises03.mainActivity.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercises03.MAIN
import com.example.exercises03.R
import com.example.exercises03.databinding.MainFragmentLayoutBinding
import com.example.exercises03.habitModel.Habit
import com.example.exercises03.mainActivity.HabitAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HabitListFragment : Fragment() {

    private lateinit var adapter: HabitAdapter
    private lateinit var binding: MainFragmentLayoutBinding

    companion object {
        private const val REQUEST_CODE_ADD_HABIT = 111
        private const val REQUEST_CODE_EDIT_HABIT = 112
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupFab()

        val receivedBundle = arguments?.getBundle("habitBundle")
        if (receivedBundle != null) {
            val editedHabit = receivedBundle.getSerializable("habit") as? Habit
            val index = receivedBundle.getInt("habitIndex")

            when (receivedBundle.getInt("actionCode")) {
                REQUEST_CODE_ADD_HABIT -> handleAddHabitResult(editedHabit, index)
                REQUEST_CODE_EDIT_HABIT -> handleEditHabitResult(editedHabit, index)
            }
        }
    }

    private fun setupListView() {
        val habitsView = binding.habitsView
        adapter = HabitAdapter(requireContext(), MAIN.habits)
        habitsView.layoutManager = LinearLayoutManager(requireContext())
        habitsView.adapter = adapter
        adapter.setOnItemClickListener { position ->
            editHabit(position)
        }
    }

    private fun editHabit(position: Int) {
        val habitToEdit = MAIN.habits[position]
        val bundle = Bundle().apply {
            putSerializable("habit", habitToEdit)
            putInt("habitId", position)
            putInt("actionCode", REQUEST_CODE_EDIT_HABIT)
        }
        MAIN.navController.navigate(R.id.action_habitListFragment_to_editHabitFragment, bundle)
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("actionCode", REQUEST_CODE_ADD_HABIT)
                putInt("habitIndex", MAIN.habits.size)
            }
            MAIN.navController.navigate(R.id.action_habitListFragment_to_editHabitFragment, bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleAddHabitResult(habit: Habit?, index: Int) {
        habit.let {
            if (it != null) {
                MAIN.habits.add(it)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun handleEditHabitResult(editedHabit: Habit?, index: Int) {

        editedHabit.let {
            if (index != -1) {
                if (editedHabit != null) {
                    MAIN.habits[index] = editedHabit
                }
                adapter.notifyItemChanged(index)
            }
        }
    }
}
