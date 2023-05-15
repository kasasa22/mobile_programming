package com.example.demo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class Rout2Fragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //When accessing a shared preference in a fragment use the requireContext() method.
        val sharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        // Inflate the layout for this fragment
         val rootView = inflater.inflate(R.layout.fragment_rout2, container, false)

        if(sharedPreferences.getString("ROUTINE_ENTRY", null) != null){
            rootView.findViewById<TextView>(R.id.display_text).text = sharedPreferences.getString("ROUTINE_ENTRY", null).toString()
        }
        return rootView
    }


}