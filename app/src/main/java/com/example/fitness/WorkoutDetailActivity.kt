package com.example.fitness

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class WorkoutDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout_detail)

        val workoutId = intent.getIntExtra("workout_id", 1)
        val workout = SampleData.workouts.find { it.id == workoutId } ?: return

        // Bind data
        findViewById<TextView>(R.id.tvDetailName).text = workout.name
        findViewById<TextView>(R.id.tvDetailCategory).text = workout.category.uppercase()
        findViewById<TextView>(R.id.tvDetailDuration).text = "${workout.duration} min"
        findViewById<TextView>(R.id.tvDetailCalories).text = "${workout.calories} cal"
        findViewById<TextView>(R.id.tvDetailDifficulty).text = workout.difficulty
        findViewById<TextView>(R.id.tvDetailDescription).text = workout.description

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Exercises RecyclerView
        val rvExercises = findViewById<RecyclerView>(R.id.rvExercises)
        rvExercises.layoutManager = LinearLayoutManager(this)
        rvExercises.adapter = ExerciseAdapter(workout.exercises)

        // Start Workout button
        findViewById<MaterialButton>(R.id.btnStartWorkout).setOnClickListener {
            Toast.makeText(this, "Workout Started! 💪", Toast.LENGTH_SHORT).show()
        }
    }
}
