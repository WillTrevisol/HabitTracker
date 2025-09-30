package com.trevisol.habittracker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.trevisol.habittracker.R
import com.trevisol.habittracker.databinding.HabitTileBinding
import com.trevisol.habittracker.domain.model.HabitWithStatus

class HabitsAdapter(
    private val onHabitClickListener: OnHabitClickListener
): ListAdapter<HabitWithStatus, HabitsAdapter.HabitsTileViewHolder>(HabitDiffCallback) {

    inner class HabitsTileViewHolder(private val tileHabitBinding: HabitTileBinding): RecyclerView.ViewHolder(tileHabitBinding.root) {

        init {
            tileHabitBinding.checkboxHabitStatus.setOnCheckedChangeListener { _, isChecked ->
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val habitWithStatus = getItem(bindingAdapterPosition)
                    onHabitClickListener.onHabitStatusChanged(habitWithStatus.habit.id, isChecked)
                }
            }
        }
        fun bind(habitWithStatus: HabitWithStatus) {
            tileHabitBinding.textHabitTitle.text = habitWithStatus.habit.name
            tileHabitBinding.textHabitDescription.text = habitWithStatus.habit.description
            tileHabitBinding.checkboxHabitStatus.isChecked = habitWithStatus.isCompletedToday
        }
    }

    companion object HabitDiffCallback : DiffUtil.ItemCallback<HabitWithStatus>() {
        override fun areItemsTheSame(oldItem: HabitWithStatus, newItem: HabitWithStatus): Boolean {
            return oldItem.habit.id == newItem.habit.id
        }

        override fun areContentsTheSame(oldItem: HabitWithStatus, newItem: HabitWithStatus): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsTileViewHolder {
        val binding = HabitTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitsTileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitsTileViewHolder, position: Int) {
        val habitWithStatus = getItem(position)

        holder.itemView.setOnClickListener {
            onHabitClickListener.onHabitClick(habitWithStatus.habit.id)
        }

        holder.itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
            (onHabitClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                R.menu.context_menu_habit,
                menu
            )

            menu.findItem(R.id.removeHabit)?.setOnMenuItemClickListener {
                onHabitClickListener.onRemoveHabitClick(habitWithStatus.habit)
                true
            }
        }

        holder.bind(habitWithStatus)
    }
}