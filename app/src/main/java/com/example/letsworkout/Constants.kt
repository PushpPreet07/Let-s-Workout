package com.example.letsworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val boxSquats = ExerciseModel(
            1,
            "\"You earn your own body\"",
            "Box Squats",
            R.drawable.boxsqats,
            false,
            false
        )
        exerciseList.add(boxSquats)

        val crossJacks = ExerciseModel(
            2,
            "\"push through the pain\"",
            "Cross Jacks",
            R.drawable.crosjacks,
            false,
            false
        )
        exerciseList.add(crossJacks)

        val legups = ExerciseModel(
            3,
            "\"no pain no gain\"",
            "Leg Ups",
            R.drawable.legupss,
            false,
            false
        )
        exerciseList.add(legups)

        val rest = ExerciseModel(
            4,
            "\"First step to success is discipline\"",
            "rest",
            R.drawable.restt,
            false,
            false
        )
        exerciseList.add(rest)

        val lunges = ExerciseModel(
            5,
            "\"Sore today Strong tomorrow\"" ,
            "Lunges",
            R.drawable.lungess,
            false,
            false
        )
        exerciseList.add(lunges)

        val pushpups = ExerciseModel(
            6,
            "\"sweat is fat crying\"",
            "Push Ups",
            R.drawable.pushups,
            false,
            false
        )
        exerciseList.add(pushpups)

        val situps = ExerciseModel(
            7,
            "\"Hustle for that muscle\"",
            "Sit Ups",
            R.drawable.situps,
            false,
            false
        )
        exerciseList.add(situps)

        return exerciseList
    }

}