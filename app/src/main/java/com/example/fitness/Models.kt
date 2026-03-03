package com.example.fitness

data class Workout(
    val id: Int,
    val name: String,
    val category: String,
    val duration: Int,       // in minutes
    val calories: Int,
    val difficulty: String,  // "Beginner", "Intermediate", "Advanced"
    val description: String,
    val exercises: List<Exercise>
)

data class Exercise(
    val name: String,
    val sets: Int,
    val reps: Int
)

data class Reminder(
    val id: Int,
    val time: String,        // e.g. "07:00 AM"
    val workoutLabel: String,
    val days: List<Boolean>,  // Mon-Sun, 7 booleans
    val isEnabled: Boolean
)
