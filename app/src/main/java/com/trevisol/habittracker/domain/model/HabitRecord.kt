package com.trevisol.habittracker.domain.model

import com.trevisol.habittracker.data.entity.HabitRecordEntity
import java.util.Date

data class HabitRecord(
    val habitId: Long,
    val date: Date,
    val isCompleted: Boolean = true
) {
    fun toEntity(): HabitRecordEntity = HabitRecordEntity(
        habitId = habitId,
        date = date,
        isCompleted = isCompleted,
    )
}