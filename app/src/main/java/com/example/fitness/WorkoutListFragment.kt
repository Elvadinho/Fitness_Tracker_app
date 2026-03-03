package com.example.fitness

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class WorkoutListFragment : Fragment() {

    private lateinit var adapter: WorkoutAdapter
    private var allWorkouts = SampleData.workouts
    private var currentCategory = "All"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workout_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set greeting based on time of day
        val tvGreeting = view.findViewById<TextView>(R.id.tvGreeting)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        tvGreeting.text = when {
            hour < 12 -> getString(R.string.greeting_morning)
            hour < 17 -> getString(R.string.greeting_afternoon)
            else -> getString(R.string.greeting_evening)
        }

        // Setup RecyclerView
        val rvWorkouts = view.findViewById<RecyclerView>(R.id.rvWorkouts)
        adapter = WorkoutAdapter(allWorkouts) { workout ->
            val intent = Intent(requireContext(), WorkoutDetailActivity::class.java)
            intent.putExtra("workout_id", workout.id)
            startActivity(intent)
        }
        rvWorkouts.layoutManager = LinearLayoutManager(requireContext())
        rvWorkouts.adapter = adapter

        // Search
        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterWorkouts(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Category chips
        val chips = mapOf(
            R.id.chipAll to "All",
            R.id.chipStrength to "Strength",
            R.id.chipCardio to "Cardio",
            R.id.chipFlexibility to "Flexibility",
            R.id.chipHiit to "HIIT"
        )

        val chipViews = chips.keys.map { view.findViewById<TextView>(it) }

        chips.forEach { (chipId, category) ->
            view.findViewById<TextView>(chipId).setOnClickListener { chipView ->
                currentCategory = category
                chipViews.forEach { cv ->
                    cv.setBackgroundResource(R.drawable.bg_chip)
                    cv.setTextColor(requireContext().getColor(R.color.primary))
                }
                (chipView as TextView).setBackgroundResource(R.drawable.bg_chip_selected)
                chipView.setTextColor(requireContext().getColor(R.color.white))
                filterWorkouts(etSearch.text.toString())
            }
        }
    }

    private fun filterWorkouts(query: String) {
        var filtered = allWorkouts
        if (currentCategory != "All") {
            filtered = filtered.filter { it.category == currentCategory }
        }
        if (query.isNotBlank()) {
            filtered = filtered.filter {
                it.name.contains(query, ignoreCase = true) ||
                it.category.contains(query, ignoreCase = true)
            }
        }
        adapter.updateList(filtered)
    }
}
