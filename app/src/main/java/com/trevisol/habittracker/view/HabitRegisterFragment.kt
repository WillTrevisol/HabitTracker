package com.trevisol.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.trevisol.habittracker.R
import com.trevisol.habittracker.databinding.FragmentHabitRegisterBinding
import com.trevisol.habittracker.domain.model.Habit
import com.trevisol.habittracker.viewmodel.HabitsViewModel
import kotlinx.coroutines.launch
import kotlin.sequences.forEach

class HabitRegisterFragment : Fragment() {

    private lateinit var fragmentHabitRegisterBinding: FragmentHabitRegisterBinding

    private val viewModel: HabitsViewModel by viewModels {
        HabitsViewModel.habitsViewModelFactory()
    }

    private var habit: Habit? = null

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



        val habitId = arguments?.getLong("habitId")
        if (habitId != null) {
            viewModel.getHabitById(habitId)

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.habit.collect { habitFromId ->
                        habit = habitFromId
                        habitFromId?.let { detail ->
                            with(fragmentHabitRegisterBinding.habitRegisterFragment) {
                                habitNameEditText.setText(detail.name)
                                habitDescriptionEditText.setText(detail.description)

                                if (detail.frequencyType == "DAILY") {
                                    frequencyToggleGroupButton.check(R.id.dailyButton)
                                    daysChipGroup.visibility = View.GONE
                                } else {
                                    frequencyToggleGroupButton.check(R.id.weeklyButton)
                                    daysChipGroup.visibility = View.VISIBLE
                                }

                                val selectedDays = detail.daysOfWeek?.split(", ")?.toSet() ?: emptySet()
                                daysChipGroup.children.forEach { view ->
                                    (view as Chip).let {
                                        it.isChecked = selectedDays.contains(it.text.toString())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        (activity as AppCompatActivity).supportActionBar?.subtitle =
            if (habitId != null) getString(R.string.habit_details)
            else getString(R.string.create_habit)

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
        var frequency = "DAILY"
        if (fragmentHabitRegisterBinding.habitRegisterFragment.frequencyToggleGroupButton.checkedButtonId == R.id.weeklyButton) {
            frequency = "WEEKLY"
        }

        var selectedDays: String? = null
        if (frequency == "WEEKLY") {
            selectedDays = fragmentHabitRegisterBinding.habitRegisterFragment.daysChipGroup.checkedChipIds.joinToString {
                view?.findViewById<Chip>(it)?.text.toString()
            }
        }

        if (habit != null) {
            habit?.let {
                it.name = habitName
                it.description = description
                it.frequencyType = frequency
                it.daysOfWeek = selectedDays
            }
            viewModel.updateHabit(habit!!)
            findNavController().popBackStack()
            return
        }

        val newHabit = Habit(
            name = habitName,
            description = description,
            frequencyType = frequency,
            daysOfWeek = selectedDays,
        )

        viewModel.insertHabit(newHabit)
        findNavController().popBackStack()
    }
}
