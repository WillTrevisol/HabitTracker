package com.trevisol.habittracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trevisol.habittracker.data.dao.HabitDao
import com.trevisol.habittracker.data.dao.HabitRecordDao
import com.trevisol.habittracker.data.entity.HabitEntity
import com.trevisol.habittracker.data.entity.HabitRecordEntity

@Database(entities = [ HabitEntity::class, HabitRecordEntity::class ], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitRecordDao(): HabitRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "habit_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}