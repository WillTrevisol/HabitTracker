package com.trevisol.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.trevisol.habittracker.R
import com.trevisol.habittracker.databinding.FragmentHabitRegisterBinding
import com.trevisol.habittracker.domain.model.Habit
import com.trevisol.habittracker.viewmodel.HabitRegisterViewModel

class HabitRegisterFragment : Fragment() {

    private lateinit var fragmentHabitRegisterBinding: FragmentHabitRegisterBinding

    private val viewModel: HabitRegisterViewModel by viewModels {
        HabitRegisterViewModel.habitRegisterViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHabitRegisterBinding = FragmentHabitRegisterBinding.inflate(inflater, container, false)
        return fragmentHabitRegisterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        fragmentHabitRegisterBinding.habitRegisterFragment.frequencyToggleGroupButton.addOnButtonCheckedListener { toggleGroup, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.dailyButton -> {
                        fragmentHabitRegisterBinding.habitRegisterFragment.daysChipGroup.visibility = View.GONE
                    }
                    R.id.weeklyButton -> {
                        fragmentHabitRegisterBinding.habitRegisterFragment.daysChipGroup.visibility = View.VISIBLE
                    }
                }
            }
        }

        fragmentHabitRegisterBinding.habitRegisterFragment.saveButton.setOnClickListener {
            saveHabit()
        }
    }

    private fun saveHabit() {
        val habitName = fragmentHabitRegisterBinding.habitRegisterFragment.habitNameEditText.text.toString()

        if (habitName.isEmpty()) {
            fragmentHabitRegisterBinding.habitRegisterFragment.habitNameTextInput.error =
                getString(R.string.habit_name_cannot_be_empty)
            return
        } else {
            fragmentHabitRegisterBinding.habitRegisterFragment.habitNameTextInput.error = null
        }

        val description = fragmentHabitRegisterBinding.habitRegisterFragment.habitDescriptionEditText.text.toString()
        var frequency: String = "DAILY"
        if (fragmentHabitRegisterBinding.habitRegisterFragment.frequencyToggleGroupButton.checkedButtonId == R.id.weeklyButton) {
            frequency = "WEEKLY"
        }

        var selectedDays: String? = null
        if (frequency == "WEEKLY") {
            selectedDays = fragmentHabitRegisterBinding.habitRegisterFragment.daysChipGroup.checkedChipIds.joinToString {
                view?.findViewById<Chip>(it)?.text.toString()
            }
        }

        val habit = Habit(
            name = habitName,
            description = description,
            frequencyType = frequency,
            daysOfWeek = selectedDays,
        )

        viewModel.saveHabit(habit)
        findNavController().popBackStack()
    }
}
