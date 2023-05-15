package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.demo.databinding.ActivityThingSelectionBinding

class ThingSelection : AppCompatActivity() {


    private lateinit var binding: ActivityThingSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityThingSelectionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Setting listener on select thing back button
        binding.thingSelectionBackButton.setOnClickListener {
            val backToCreateRoutine = Intent(this, CreateRoutine::class.java)
            startActivity(backToCreateRoutine)
        }

        binding.notificationIcon.setOnClickListener {
            binding.notificationContainer.visibility = View.GONE
            binding.invisibleText.visibility = View.VISIBLE
        }

        binding.invisibleText.setOnClickListener {
            val moveToCreateRoutine = Intent(this, CreateRoutine::class.java)
            moveToCreateRoutine.putExtra("Alert_Box", "Instantiate Alert Dialog")
            startActivity(moveToCreateRoutine)

        }



    }
}