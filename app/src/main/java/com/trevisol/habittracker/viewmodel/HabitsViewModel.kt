package com.trevisol.habittracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.trevisol.habittracker.HabitApplication
import com.trevisol.habittracker.domain.model.Habit
import com.trevisol.habittracker.domain.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitsViewModel(private val repository: HabitRepository): ViewModel() {

    val habits = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit)
    }

    fun updateHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habit)
    }

    fun removeHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(habit)
    }

    private val _habit = MutableStateFlow<Habit?>(null)
    val habit: StateFlow<Habit?> = _habit.asStateFlow()

    fun getHabitById(id: Long) {
        viewModelScope.launch {
            repository.getHabitById(id).collect { habitEntity ->
                _habit.value = habitEntity
            }
        }
    }

    companion object {
        fun habitsViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(
                        extras[APPLICATION_KEY]
                    )
                    return HabitsViewModel(
                        (application as HabitApplication).habitRepository
                    ) as T
                }
            }
    }
}
