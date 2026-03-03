package com.example.fitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val exercises: List<Exercise>
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvExerciseNumber: TextView = itemView.findViewById(R.id.tvExerciseNumber)
        val tvExerciseName: TextView = itemView.findViewById(R.id.tvExerciseName)
        val tvExerciseDetail: TextView = itemView.findViewById(R.id.tvExerciseDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.tvExerciseNumber.text = "${position + 1}"
        holder.tvExerciseName.text = exercise.name
        holder.tvExerciseDetail.text = "${exercise.sets} sets × ${exercise.reps} reps"

        // Animate
        holder.itemView.alpha = 0f
        holder.itemView.translationX = 40f
        holder.itemView.animate()
            .alpha(1f)
            .translationX(0f)
            .setDuration(250)
            .setStartDelay((position * 50).toLong())
            .start()
    }

    override fun getItemCount() = exercises.size
}
