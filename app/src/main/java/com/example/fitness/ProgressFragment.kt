package com.example.fitness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Stat cards
        val tvTotalWorkouts = view.findViewById<TextView>(R.id.tvTotalWorkouts)
        val tvTotalCalories = view.findViewById<TextView>(R.id.tvTotalCalories)
        val tvCurrentStreak = view.findViewById<TextView>(R.id.tvCurrentStreak)

        tvTotalWorkouts.text = "24"
        tvTotalCalories.text = "8,450"
        tvCurrentStreak.text = "7"

        // Chart
        val chartView = view.findViewById<ProgressChartView>(R.id.chartView)
        chartView.setData(SampleData.weeklyWorkouts, SampleData.weeklyLabels)

        // Weekly/Monthly toggle
        val btnWeekly = view.findViewById<TextView>(R.id.btnWeekly)
        val btnMonthly = view.findViewById<TextView>(R.id.btnMonthly)

        btnWeekly.setOnClickListener {
            btnWeekly.setBackgroundResource(R.drawable.bg_chip_selected)
            btnWeekly.setTextColor(requireContext().getColor(R.color.white))
            btnMonthly.background = null
            btnMonthly.setTextColor(requireContext().getColor(R.color.primary))
            chartView.setData(SampleData.weeklyWorkouts, SampleData.weeklyLabels)
        }

        btnMonthly.setOnClickListener {
            btnMonthly.setBackgroundResource(R.drawable.bg_chip_selected)
            btnMonthly.setTextColor(requireContext().getColor(R.color.white))
            btnWeekly.background = null
            btnWeekly.setTextColor(requireContext().getColor(R.color.primary))
            // Show monthly data (simulated)
            val monthlyData = listOf(12, 10, 15, 8)
            val monthlyLabels = listOf("Week 1", "Week 2", "Week 3", "Week 4")
            chartView.setData(monthlyData, monthlyLabels)
        }

        // Animate stat cards
        listOf(tvTotalWorkouts, tvTotalCalories, tvCurrentStreak).forEachIndexed { index, tv ->
            val card = (tv.parent as View).parent as View
            card.alpha = 0f
            card.translationY = 30f
            card.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setStartDelay((index * 100 + 100).toLong())
                .start()
        }
    }
}
