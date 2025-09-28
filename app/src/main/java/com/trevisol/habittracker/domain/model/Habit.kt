package com.trevisol.habittracker.domain.model

import com.trevisol.habittracker.data.entity.HabitEntity

data class Habit(
    val id: Long = 0,
    val name: String,
    val description: String,
    val frequencyType: String,
    val daysOfWeek: String? = null,
    val status: Boolean = false,
) {
    fun toEntity(): HabitEntity = HabitEntity(
        id = id,
        name = name,
        description = description,
        frequencyType = frequencyType,
        daysOfWeek = daysOfWeek,
        status = status,
    )
}
