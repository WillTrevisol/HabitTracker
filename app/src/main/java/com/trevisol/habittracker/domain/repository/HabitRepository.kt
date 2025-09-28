package com.trevisol.habittracker.domain.repository

import com.trevisol.habittracker.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun insert(habit: Habit)
    suspend fun update(habit: Habit)
    suspend fun delete(habit: Habit)
    fun getAll(): Flow<List<Habit>>
}