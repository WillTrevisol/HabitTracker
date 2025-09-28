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
import kotlinx.coroutines.launch

class HabitRegisterViewModel(private val repository: HabitRepository) : ViewModel() {

    fun saveHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit)
    }

    companion object {
        fun habitRegisterViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(
                        extras[APPLICATION_KEY]
                    )
                    return HabitRegisterViewModel(
                        (application as HabitApplication).habitRepository
                    ) as T
                }
            }
    }
}