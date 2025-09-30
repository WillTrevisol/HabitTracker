package com.trevisol.habittracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.trevisol.habittracker.data.entity.HabitRecordEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface HabitRecordDao {

    @Query("SELECT * FROM habit_records WHERE date=:date")
    fun getRecordsForDate(date: Date): Flow<List<HabitRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(habitRecord: HabitRecordEntity)

    @Query("DELETE FROM habit_records WHERE habitId=:habitId AND date=:date")
    suspend fun deleteRecord(habitId: Long, date: Date)

}
