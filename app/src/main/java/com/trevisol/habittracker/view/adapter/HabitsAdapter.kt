package com.trevisol.habittracker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trevisol.habittracker.R
import com.trevisol.habittracker.databinding.HabitTileBinding
import com.trevisol.habittracker.domain.model.Habit

class HabitsAdapter(
    private val onHabitClickListener: OnHabitClickListener
): ListAdapter<Habit, HabitsAdapter.HabitsTileViewHolder>(HabitDiffCallback) {

    inner class HabitsTileViewHolder(private val tileHabitBinding: HabitTileBinding): RecyclerView.ViewHolder(tileHabitBinding.root) {
        fun bind(habit: Habit) {
            tileHabitBinding.textHabitTitle.text = habit.name
            tileHabitBinding.checkboxHabitStatus.isChecked = habit.status
        }
    }

    companion object HabitDiffCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsTileViewHolder {
        val binding = HabitTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitsTileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitsTileViewHolder, position: Int) {
        val habit = getItem(position)

        holder.itemView.setOnClickListener {
            onHabitClickListener.onHabitClick(habit.id)
        }

        holder.itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
            (onHabitClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                R.menu.context_menu_habit,
                menu
            )

            menu.findItem(R.id.removeHabit)?.setOnMenuItemClickListener {
                onHabitClickListener.onRemoveHabitClick(habit)
                true
            }
        }

        holder.bind(habit)
    }
}