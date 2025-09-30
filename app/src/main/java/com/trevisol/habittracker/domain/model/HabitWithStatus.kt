package com.trevisol.habittracker.domain.model

data class HabitWithStatus(
    val habit: Habit,
    val isCompletedToday: Boolean
)
