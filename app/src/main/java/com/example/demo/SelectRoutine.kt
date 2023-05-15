package com.example.demo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.demo.databinding.ActivitySelectRoutineBinding

class SelectRoutine : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRoutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        binding = ActivitySelectRoutineBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.createRoutineBackButton.setOnClickListener {
            val backToMain = Intent(this, MainActivity::class.java)
            startActivity(backToMain)

        }

        binding.createRoutineButton.setOnClickListener {
            val moveToCreateRoutine = Intent(this, CreateRoutine::class.java)
            startActivity(moveToCreateRoutine)
        }

        if(sharedPreferences.getString("ROUTINE_ENTRY",null) != null){
            binding.displayText1.text = sharedPreferences.getString("ROUTINE_ENTRY", null)
            binding.invisibleLinearLayout.visibility = View.VISIBLE
        }
    }
}