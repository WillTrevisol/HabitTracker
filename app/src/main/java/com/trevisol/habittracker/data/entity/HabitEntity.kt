package com.trevisol.habittracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trevisol.habittracker.domain.model.Habit

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val frequencyType: String,
    val daysOfWeek: String? = null,
    val status: Boolean = false
) {
    fun toDomain(): Habit = Habit(
        id = id,
        name = name,
        description = description,
        frequencyType = frequencyType,
        daysOfWeek = daysOfWeek,
        status = false,
    )
}
