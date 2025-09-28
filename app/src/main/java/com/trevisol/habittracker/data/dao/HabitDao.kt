package com.trevisol.habittracker.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.trevisol.habittracker.data.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits ORDER BY id DESC")
    fun getAll(): Flow<List<HabitEntity>>

    @Insert()
    suspend fun insert(habitEntity: HabitEntity)

    @Update
    suspend fun update(habitEntity: HabitEntity)

    @Delete
    suspend fun delete(habitEntity: HabitEntity)

    @Query("SELECT * FROM habits WHERE id=:id")
    fun getHabitById(id: Long): Flow<HabitEntity>

}