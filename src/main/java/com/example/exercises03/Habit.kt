package com.example.exercises03

import java.io.Serializable

class Habit(
    val id: Int,
    val title: String,
    val description: String,
    val priority: String,
    val type: String,
    val amount: String,
    val frequency: String,
    val color: Int,
) : Serializable {
    override fun toString(): String {
        return "$title\n$description\nPriority: $priority\nType:" +
                " $type\nFrequency: $amount $frequency\nColor: $color"
    }
}
