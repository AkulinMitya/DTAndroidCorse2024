package com.example.exercises03.habitModel

enum class HabitType(val typeName: String) {
    Bad("Bad"),
    Good("Good"),
    Empty("");

    companion object {
        fun habitTypeFromString(text: String): HabitType {
            return when (text) {
                "Bad" -> Bad
                "Good" -> Good
                else -> Empty
            }
        }
    }
}