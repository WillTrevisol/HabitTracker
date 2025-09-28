package com.trevisol.habittracker

import android.app.Application
import com.trevisol.habittracker.data.database.AppDatabase
import com.trevisol.habittracker.data.repository.HabitRepositoryImpl
import kotlin.getValue

class HabitApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val habitRepository by lazy { HabitRepositoryImpl(database.habitDao()) }
}