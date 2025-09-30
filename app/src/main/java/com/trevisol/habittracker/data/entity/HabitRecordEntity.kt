package com.trevisol.habittracker.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.trevisol.habittracker.domain.model.HabitRecord
import java.util.Date

@Entity(
    tableName = "habit_records",
    primaryKeys = [ "habitId", "date" ],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = [ "id" ],
            childColumns = [ "habitId" ],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HabitRecordEntity(
    val habitId: Long,
    val date: Date,
    val isCompleted: Boolean = true
) {
    fun toDomain(): HabitRecord = HabitRecord(
        habitId = habitId,
        date = date,
        isCompleted = isCompleted,
    )
}