package com.example.exercises03

enum class HabitPriority(val priorityName: String) {
    Low("Low"),
    Medium("Medium"),
    High("High"),
    Extreme("Extreme"),
    Empty("");
    companion object {
         fun habitPriorityFromString(text: String): HabitPriority {
            return when(text) {
                "Low" -> Low
                "Medium" -> Medium
                "High" -> High
                "Extreme" -> Extreme
                else -> Empty
            }
        }
    }
}
