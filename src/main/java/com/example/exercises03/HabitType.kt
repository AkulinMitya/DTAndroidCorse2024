package com.example.exercises03

enum class HabitType(val typeName: String) {
    Sport("Sport"),
    Study("Study"),
    Music("Music"),
    Empty("");

    companion object {
        fun habitTypeFromString(text: String): HabitType {
            return when (text) {
                "Sport" -> Sport
                "Study" -> Study
                "Music" -> Music
                else -> Empty
            }
        }
    }
}