package com.trevisol.habittracker.domain.model

import com.trevisol.habittracker.data.entity.HabitEntity

data class Habit(
    val id: Long = 0,
    var name: String,
    var description: String,
    var frequencyType: String,
    var daysOfWeek: String? = null,
    var status: Boolean = false,
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
