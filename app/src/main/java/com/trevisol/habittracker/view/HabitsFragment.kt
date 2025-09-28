package com.trevisol.habittracker.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.trevisol.habittracker.R
import com.trevisol.habittracker.databinding.FragmentHabitsBinding
import com.trevisol.habittracker.view.adapter.HabitsAdapter
import com.trevisol.habittracker.viewmodel.HabitsViewModel
import kotlinx.coroutines.launch

class HabitsFragment : Fragment() {

    private lateinit var habitsFragmentBinding: FragmentHabitsBinding

    private val viewModel: HabitsViewModel by viewModels {
        HabitsViewModel.habitsViewModelFactory()
    }

    private val habitsAdapter = HabitsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        habitsFragmentBinding = FragmentHabitsBinding.inflate(inflater, container, false)

        setupRecyclerView()

        habitsFragmentBinding.fab.setOnClickListener {
            findNavController().navigate(R.id.habitRegisterFragment)
        }
        return habitsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.habits.collect {
                    Log.d("HabitsFragment", "Lista de habitos recebida: $it")
                    habitsAdapter.submitList(it)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        habitsFragmentBinding.habitRecyclerView.apply {
            adapter = habitsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
