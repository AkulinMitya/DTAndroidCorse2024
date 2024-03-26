package com.example.exercises03

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class HabitAdapter(private val context: Context, private var habits: List<Habit>) :
    RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorSquare: View = itemView.findViewById(R.id.colorSquare)
        val habitId: TextView = itemView.findViewById(R.id.habitId)
        val habitTitle: TextView = itemView.findViewById(R.id.habitTitle)
        val habitDescription: TextView = itemView.findViewById(R.id.habitDescription)
        val habitPriority: TextView = itemView.findViewById(R.id.habitPriority)
        val habitType: TextView = itemView.findViewById(R.id.habitType)
        val habitAmount: TextView = itemView.findViewById(R.id.habitAmount)
        val habitFrequency: TextView = itemView.findViewById(R.id.habitFrequency)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_habit, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habit = habits[position]
        holder.habitId.text = habit.id.toString()
        holder.habitTitle.text = habit.title
        holder.habitDescription.text = habit.description
        holder.habitPriority.text = habit.priority.priorityName
        holder.habitType.text = habit.type.typeName
        holder.habitAmount.text = habit.amount
        holder.habitFrequency.text = habit.frequency.freqName
        holder.colorSquare.setBackgroundColor(habit.color)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(position)
        }
    }

    private var onItemClickListener: ((position: Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        onItemClickListener = listener
    }

}

