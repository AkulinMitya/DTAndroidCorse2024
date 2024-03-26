package com.example.exercises03

import java.io.Serializable

class Habit(
    val id: Int,
    val title: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val amount: String,
    val frequency: HabitFrequency,
    val color: Int,
) : Serializable {
}
