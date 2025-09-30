package com.trevisol.habittracker.data.repository

import com.trevisol.habittracker.data.dao.HabitRecordDao
import com.trevisol.habittracker.domain.model.HabitRecord
import com.trevisol.habittracker.domain.repository.HabitRecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

class HabitRecordRepositoryImpl(private val habitRecordDao: HabitRecordDao): HabitRecordRepository {

    override fun getRecordsForDate(date: Date): Flow<List<HabitRecord>> = habitRecordDao.getRecordsForDate(date).map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun insertRecord(habitRecord: HabitRecord) {
        habitRecordDao.insertRecord(habitRecord.toEntity())
    }

    override suspend fun deleteRecord(habitId: Long, date: Date) {
        habitRecordDao.deleteRecord(habitId, date)
    }

}
