package com.example.fitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class WorkoutAdapter(
    private var workouts: List<Workout>,
    private val onItemClick: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewCategoryColor: View = itemView.findViewById(R.id.viewCategoryColor)
        val tvWorkoutName: TextView = itemView.findViewById(R.id.tvWorkoutName)
        val tvWorkoutCategory: TextView = itemView.findViewById(R.id.tvWorkoutCategory)
        val tvWorkoutDuration: TextView = itemView.findViewById(R.id.tvWorkoutDuration)
        val tvWorkoutCalories: TextView = itemView.findViewById(R.id.tvWorkoutCalories)
        val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        val context = holder.itemView.context

        holder.tvWorkoutName.text = workout.name
        holder.tvWorkoutCategory.text = workout.category
        holder.tvWorkoutDuration.text = "${workout.duration} min"
        holder.tvWorkoutCalories.text = "${workout.calories} cal"
        holder.tvDifficulty.text = workout.difficulty

        // Category color
        val colorRes = when (workout.category) {
            "Strength" -> R.color.category_strength
            "Cardio" -> R.color.category_cardio
            "Flexibility" -> R.color.category_flexibility
            "HIIT" -> R.color.category_hiit
            else -> R.color.primary
        }
        holder.viewCategoryColor.setBackgroundColor(ContextCompat.getColor(context, colorRes))

        // Animate item appearance
        holder.itemView.alpha = 0f
        holder.itemView.translationY = 30f
        holder.itemView.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(300)
            .setStartDelay((position * 60).toLong())
            .start()

        holder.itemView.setOnClickListener { onItemClick(workout) }
    }

    override fun getItemCount() = workouts.size

    fun updateList(newWorkouts: List<Workout>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }
}
