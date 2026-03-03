package com.example.fitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.materialswitch.MaterialSwitch

class ReminderAdapter(
    private var reminders: List<Reminder>
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvReminderTime: TextView = itemView.findViewById(R.id.tvReminderTime)
        val tvReminderWorkout: TextView = itemView.findViewById(R.id.tvReminderWorkout)
        val switchReminder: MaterialSwitch = itemView.findViewById(R.id.switchReminder)
        val daysContainer: View = itemView.findViewById(R.id.daysContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.tvReminderTime.text = reminder.time
        holder.tvReminderWorkout.text = reminder.workoutLabel
        holder.switchReminder.isChecked = reminder.isEnabled

        // Set day indicators
        val daysContainer = holder.daysContainer as? android.widget.LinearLayout
        if (daysContainer != null && daysContainer.childCount == 7) {
            for (i in 0 until 7) {
                val dayView = daysContainer.getChildAt(i) as? TextView
                if (dayView != null && i < reminder.days.size) {
                    if (reminder.days[i]) {
                        dayView.setBackgroundResource(R.drawable.bg_chip_selected)
                        dayView.setTextColor(holder.itemView.context.getColor(R.color.white))
                    } else {
                        dayView.setBackgroundResource(R.drawable.bg_chip)
                        dayView.setTextColor(holder.itemView.context.getColor(R.color.primary))
                    }
                }
            }
        }

        // Animate
        holder.itemView.alpha = 0f
        holder.itemView.translationY = 20f
        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(300)
            .setStartDelay((position * 80).toLong())
            .start()
    }

    override fun getItemCount() = reminders.size

    fun updateList(newReminders: List<Reminder>) {
        reminders = newReminders
        notifyDataSetChanged()
    }
}
