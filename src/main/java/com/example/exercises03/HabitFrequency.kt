package com.example.exercises03

enum class HabitFrequency(val freqName: String) {
    DAY("times in a day"),
    WEEK("times in a week"),
    MONTH("times in a month"),
    YEAR("times in a year"),
    Empty("");

    companion object {
        fun habitFrequencyFromString(text: String): HabitFrequency {
            return when (text) {
                "times in a day" -> DAY
                "times in a week" -> WEEK
                "times in a month" -> MONTH
                "times in a year" -> YEAR
                else -> Empty
            }
        }

    }
}