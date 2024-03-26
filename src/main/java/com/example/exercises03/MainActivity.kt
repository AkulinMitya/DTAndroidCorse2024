package com.example.exercises03

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {
    private val habits = ArrayList<Habit>()
    private lateinit var adapter: HabitAdapter

    companion object {
        private const val REQUEST_CODE_ADD_HABIT = 111
        private const val REQUEST_CODE_EDIT_HABIT = 112
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO findViewById заменить
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        setupListView()
        setupFab()
    }

    private fun setupListView() {
        val habitsView = findViewById<RecyclerView>(R.id.habitsView)
        adapter = HabitAdapter(this, habits)
        habitsView.layoutManager = LinearLayoutManager(this)
        habitsView.adapter = adapter
        adapter.setOnItemClickListener { position ->
            editHabit(position)
        }
    }

    private fun editHabit(position: Int) {
        val habitToEdit = habits[position]
        val intent = Intent(this@MainActivity, EditActivity::class.java)
        intent.putExtra("habitToEdit", habitToEdit)
        intent.putExtra("habitId", position)
        startActivityForResult(intent, REQUEST_CODE_EDIT_HABIT)
    }

    private fun setupFab() {
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, EditActivity::class.java)
            intent.putExtra("habitId", habits.size)
            startActivityForResult(intent, REQUEST_CODE_ADD_HABIT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_ADD_HABIT -> handleAddHabitResult(resultCode, data)
            REQUEST_CODE_EDIT_HABIT -> handleEditHabitResult(resultCode, data)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleAddHabitResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val habit = data?.getSerializableExtra("habit") as? Habit
            habit?.let {
                habits.add(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleEditHabitResult(resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val editedHabit = data?.getSerializableExtra("habit") as? Habit

            editedHabit?.let { editedHabit ->
                val index = habits.indexOfFirst { it.id == editedHabit.id }
                if (index != -1) {
                    habits[index] = editedHabit
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}