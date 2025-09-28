package com.trevisol.habittracker.data.repository

import com.trevisol.habittracker.data.dao.HabitDao
import com.trevisol.habittracker.domain.model.Habit
import com.trevisol.habittracker.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl(private val dao: HabitDao): HabitRepository {

    override suspend fun insert(habit: Habit) {
        val entity = habit.toEntity()
        dao.insert(entity)
    }

    override suspend fun update(habit: Habit) {
        val entity = habit.toEntity()
        dao.update(entity)
    }

    override suspend fun delete(habit: Habit) {
        val entity = habit.toEntity()
        dao.delete(entity)
    }

    override fun getAll(): Flow<List<Habit>> = dao.getAll().map {
        entities -> entities.map { it.toDomain() }
    }

    override fun getHabitById(id: Long): Flow<Habit> = dao.getHabitById(id).filterNotNull().map {
        it.toDomain()
    }

}