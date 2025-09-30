package com.trevisol.habittracker.view.adapter

import com.trevisol.habittracker.domain.model.Habit

interface OnHabitClickListener {
    fun onHabitClick(id: Long)
    fun onRemoveHabitClick(habit: Habit)
    fun onHabitStatusChanged(id: Long, isChecked: Boolean)
}