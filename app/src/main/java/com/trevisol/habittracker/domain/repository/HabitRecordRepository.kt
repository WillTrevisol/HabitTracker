package com.trevisol.habittracker.domain.repository

import com.trevisol.habittracker.domain.model.HabitRecord
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface HabitRecordRepository {
    fun getRecordsForDate(date: Date): Flow<List<HabitRecord>>
    suspend fun insertRecord(habitRecord: HabitRecord)
    suspend fun deleteRecord(habitId: Long, date: Date)
}
