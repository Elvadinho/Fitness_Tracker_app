package com.example.fitness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RemindersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvReminders = view.findViewById<RecyclerView>(R.id.rvReminders)
        val emptyState = view.findViewById<View>(R.id.emptyState)
        val fabAddReminder = view.findViewById<FloatingActionButton>(R.id.fabAddReminder)

        val reminders = SampleData.reminders

        if (reminders.isEmpty()) {
            rvReminders.visibility = View.GONE
            emptyState.visibility = View.VISIBLE
        } else {
            rvReminders.visibility = View.VISIBLE
            emptyState.visibility = View.GONE

            val adapter = ReminderAdapter(reminders)
            rvReminders.layoutManager = LinearLayoutManager(requireContext())
            rvReminders.adapter = adapter
        }

        fabAddReminder.setOnClickListener {
            Toast.makeText(requireContext(), "Add Reminder", Toast.LENGTH_SHORT).show()
        }
    }
}
