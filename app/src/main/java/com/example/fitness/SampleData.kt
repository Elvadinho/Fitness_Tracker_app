package com.example.fitness

object SampleData {

    val workouts = listOf(
        Workout(
            id = 1,
            name = "Full Body Strength",
            category = "Strength",
            duration = 45,
            calories = 350,
            difficulty = "Intermediate",
            description = "A comprehensive full-body strength training workout targeting all major muscle groups. Perfect for building lean muscle and improving overall fitness.",
            exercises = listOf(
                Exercise("Barbell Squats", 4, 10),
                Exercise("Bench Press", 4, 8),
                Exercise("Deadlifts", 3, 8),
                Exercise("Overhead Press", 3, 10),
                Exercise("Bent-over Rows", 3, 12),
                Exercise("Plank Hold", 3, 45)
            )
        ),
        Workout(
            id = 2,
            name = "Morning Cardio Blast",
            category = "Cardio",
            duration = 30,
            calories = 280,
            difficulty = "Beginner",
            description = "An energizing morning cardio routine to kickstart your day. Combines jogging, jumping jacks, and bodyweight movements for a full calorie-torching session.",
            exercises = listOf(
                Exercise("Jumping Jacks", 3, 20),
                Exercise("High Knees", 3, 15),
                Exercise("Burpees", 3, 10),
                Exercise("Mountain Climbers", 3, 15),
                Exercise("Jump Squats", 3, 12)
            )
        ),
        Workout(
            id = 3,
            name = "Yoga Flow",
            category = "Flexibility",
            duration = 40,
            calories = 180,
            difficulty = "Beginner",
            description = "A calming yoga flow that improves flexibility, balance, and mental clarity. Includes sun salutations, warrior poses, and deep stretches.",
            exercises = listOf(
                Exercise("Sun Salutation A", 5, 1),
                Exercise("Warrior I", 3, 1),
                Exercise("Warrior II", 3, 1),
                Exercise("Triangle Pose", 3, 1),
                Exercise("Downward Dog", 3, 1),
                Exercise("Child's Pose", 2, 1)
            )
        ),
        Workout(
            id = 4,
            name = "HIIT Inferno",
            category = "HIIT",
            duration = 25,
            calories = 400,
            difficulty = "Advanced",
            description = "An intense high-intensity interval training session designed to push your limits. Short bursts of maximum effort followed by brief recovery periods.",
            exercises = listOf(
                Exercise("Sprint Intervals", 6, 1),
                Exercise("Box Jumps", 4, 12),
                Exercise("Battle Ropes", 4, 1),
                Exercise("Kettlebell Swings", 4, 15),
                Exercise("Plyometric Push-ups", 3, 10)
            )
        ),
        Workout(
            id = 5,
            name = "Upper Body Power",
            category = "Strength",
            duration = 50,
            calories = 320,
            difficulty = "Intermediate",
            description = "Focused upper body workout to develop strength in your chest, shoulders, back, and arms. Uses compound and isolation exercises.",
            exercises = listOf(
                Exercise("Pull-ups", 4, 8),
                Exercise("Dumbbell Shoulder Press", 4, 10),
                Exercise("Cable Chest Fly", 3, 12),
                Exercise("Bicep Curls", 3, 12),
                Exercise("Tricep Dips", 3, 12),
                Exercise("Face Pulls", 3, 15)
            )
        ),
        Workout(
            id = 6,
            name = "Core Crusher",
            category = "Strength",
            duration = 20,
            calories = 200,
            difficulty = "Intermediate",
            description = "An intense core-focused workout that targets your abs, obliques, and lower back. Build a strong foundation for all your other workouts.",
            exercises = listOf(
                Exercise("Crunches", 4, 20),
                Exercise("Russian Twists", 3, 15),
                Exercise("Leg Raises", 3, 12),
                Exercise("Bicycle Crunches", 3, 20),
                Exercise("Plank", 3, 45)
            )
        ),
        Workout(
            id = 7,
            name = "5K Run Training",
            category = "Cardio",
            duration = 35,
            calories = 320,
            difficulty = "Beginner",
            description = "A structured running program to help you build up to a 5K race. Alternates between walking and jogging intervals.",
            exercises = listOf(
                Exercise("Warm-up Walk", 1, 1),
                Exercise("Jog Interval", 5, 1),
                Exercise("Walk Recovery", 5, 1),
                Exercise("Sprint Finish", 1, 1),
                Exercise("Cool-down Walk", 1, 1)
            )
        )
    )

    val reminders = listOf(
        Reminder(
            id = 1,
            time = "07:00 AM",
            workoutLabel = "Morning Cardio",
            days = listOf(true, false, true, false, true, false, false),
            isEnabled = true
        ),
        Reminder(
            id = 2,
            time = "06:00 PM",
            workoutLabel = "Evening Strength",
            days = listOf(false, true, false, true, false, false, false),
            isEnabled = true
        ),
        Reminder(
            id = 3,
            time = "08:00 AM",
            workoutLabel = "Weekend Yoga",
            days = listOf(false, false, false, false, false, true, true),
            isEnabled = false
        )
    )

    // Weekly workout data for chart (Mon-Sun)
    val weeklyWorkouts = listOf(3, 2, 4, 1, 3, 5, 2)
    val weeklyLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
}
