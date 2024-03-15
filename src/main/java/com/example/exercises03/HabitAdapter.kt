package com.example.exercises03

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HabitAdapter(context: Context, habits: List<Habit>) : ArrayAdapter<Habit>(context, 0, habits) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_habit, parent, false)
        }

        val habit = getItem(position)

        val habitNameTextView = view!!.findViewById<TextView>(R.id.habitNameTextView)
        val colorSquare = view.findViewById<View>(R.id.colorSquare)

        habitNameTextView.text = habit.toString()
        colorSquare.setBackgroundColor(habit?.color ?: Color.BLACK)

        return view
    }
}

