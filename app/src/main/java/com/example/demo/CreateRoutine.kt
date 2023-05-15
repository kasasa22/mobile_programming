package com.example.demo

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demo.databinding.ActivityCreateRoutineBinding
import org.w3c.dom.Text
import java.util.Calendar
import androidx.appcompat.app.AlertDialog


class CreateRoutine : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRoutineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //Obtaining message from SelectEvent activity to open TimePickerDialog
        val payload = intent.getStringExtra("TIMEPICKER")
        //Obtaining message from ThingSelection to instantiate AlertDialog
        val payload2 = intent.getStringExtra("Alert_Box")


        //creating an instance of the SharedPreferences class by calling getSharedPreferences()
        /* getSharedPreferences() takes two parameters, the name of the shared preferences file and
        mode in which the file should be opened.
        MODE_PRIVATE means the shared preferences file can only be accessed by the calling and
        cannot be read by or modified by any other applications.
        */
        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)

        //Inflating the layout
        binding = ActivityCreateRoutineBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Adding listener to the cancel icon
        binding.cancelButton.setOnClickListener {
            val backToSelectRoutine = Intent(this, SelectRoutine::class.java)
            startActivity(backToSelectRoutine)
        }

        //Adding listener to the Add event floating action button
        binding.addEvent.setOnClickListener {
            val moveToSelectEvent = Intent(this, SelectEvent::class.java)
            startActivity(moveToSelectEvent)
        }




        //Settings listener on check icon
        binding.saveIcon.setOnClickListener {
            val storeTextEntered = binding.enterText.text.toString()
            //We use the edit() method to create a instance of the Editor class which we use to modify the shared preference file.
            val editor = sharedPreferences.edit()
            editor.putString("ROUTINE_ENTRY", storeTextEntered)
            editor.commit()
        }



        //changing the visibility of the  Hint when the EditText view is clicked.
        //An onFocusChangeListener is added to the EditText view so that whenever its focus changes, the hint is shown.
        //Focus refers to the state of a view receiving user input, such as touch or keyboard input.
        //Whenever a view has focus, it means it is currently receiving input from the user.
        //Focus can be transferred between two different views such as when a user taps on a different EditText field.
        binding.enterText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.invisibleTextview.visibility = View.VISIBLE
            } else {
                binding.invisibleTextview.visibility = View.INVISIBLE
            }
        } //The underscore tells the compiler that the parameter is intentionally unused.



        //Implementing the timePickerDialog
        val calendarInstance = Calendar.getInstance()
        val hour = calendarInstance.get(Calendar.HOUR_OF_DAY)
        val minute = calendarInstance.get(Calendar.MINUTE)

        if (payload != null && payload == "The time is time"){

            //TimePickerDialog object created by calling the timePickerDialog constructor
            // TimePickerDialog(context, listener, hour, minute, is24HourView boolean)
            //Using a lambda expression inside the timePickerDialog constructor to handle the selection of the time value.
            //Context is set to "this"
            // hourOfDay and minute represent the selected hour and minute values.
            val timeDialog = TimePickerDialog(this@CreateRoutine, TimePickerDialog.OnTimeSetListener{
                _, hourOfDay, minute ->

                // AM and PM implementation.
                var amOrPm = ""
                amOrPm = if(hourOfDay >= 12){
                    "PM"
                }else{
                    "AM"
                }
                //Build text to be displayed
                //The time is 00:00 AM/PM
                val stringBuilder = StringBuilder()
                stringBuilder.append("The time is ")
                stringBuilder.append(hourOfDay)
                stringBuilder.append(":")
                stringBuilder.append(minute)
                stringBuilder.append(" ")
                stringBuilder.append(amOrPm)
               val text = stringBuilder.toString()

                //message stored in shared preference
                val editor2 = sharedPreferences.edit()
                editor2.putString("TIME_MESSAGE", text)
                editor2.commit()

                //Spannable string object is created using the string stored in the TIME_MESSAGE shared preference.
                //A Spannable string object is a special text string which can be formatted and styled at specific parts of the string
                //A section  of the Spannable string object is made bold.
                //SPAN_EXCLUSIVE_EXCLUSIVE is a flag used when applying a span to a Spannable Object.
                //The span is applied to the text range specified by the start and end indices.
               val spannable = SpannableString(sharedPreferences.getString("TIME_MESSAGE", null))
                val boldSpan = StyleSpan(Typeface.BOLD)
               val endIndex = text.length
                spannable.setSpan(boldSpan, 12, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)



                //Adding text to textview
                binding.showTime.text = spannable

                //Changing visibility of the views once time is captured
                binding.visibleLayout.visibility =  View.GONE
                binding.invisibleLayout.visibility = View.VISIBLE
                binding.addConditionLayout.visibility = View.VISIBLE


            },hour, minute, false)
            timeDialog.show()

        }

        //Display time that was previously entered using the time picker dialog
        if(sharedPreferences.getString("TIME_MESSAGE", null) != null){

            val spannable = SpannableString(sharedPreferences.getString("TIME_MESSAGE", null))
            val boldSpan = StyleSpan(Typeface.BOLD)
            val endIndex = spannable.length
            spannable.setSpan(boldSpan, 12, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            //Adding text to textview
            binding.showTime.text = spannable

            //Changing visibility of the views
            binding.visibleLayout.visibility =  View.GONE
            binding.invisibleLayout.visibility = View.VISIBLE
            binding.addConditionLayout.visibility = View.VISIBLE
            binding.visibleTextview3.visibility = View.GONE
            binding.invisibleTextview2.visibility = View.VISIBLE
        }

        //Setting listener on Add Action Button
        binding.addAction.setOnClickListener {
            val moveToThingSelectionActivity = Intent(this, ThingSelection::class.java)
            startActivity(moveToThingSelectionActivity)
        }



        //Opening Alert Dialog
        if(payload2 !== null && payload2 == "Instantiate Alert Dialog"){

            //creating AlertDialog object
            val builder = AlertDialog.Builder(this@CreateRoutine)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.alert_dialog_text_entry, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.alert_text)
            val editor4 = sharedPreferences.edit()

            with(builder) {
                setTitle("Enter a value")
                //OK button
                setPositiveButton("OK"){ _, _ ->
                    //Saves entered text inside a shared preference
                    editor4.putString("Alert_Dialog_text", editText.text.toString())
                    editor4.commit()

                    //Progress Bar runs after text is saved
                    val builder2 = AlertDialog.Builder(this@CreateRoutine)
                    val inflater2 = layoutInflater
                    val dialogLayout2 = inflater2.inflate(R.layout.progress_bar, null)
                    builder2.setView(dialogLayout2)
                    val progressBarDialog = builder2.create()
                    progressBarDialog.show()
                    Handler(Looper.getMainLooper()).postDelayed(
                        {   progressBarDialog.dismiss()
                            //Building Notification message
                            val stringBuilder2 = StringBuilder()
                            stringBuilder2.append("Send Notification: ")
                            stringBuilder2.append(sharedPreferences.getString("Alert_Dialog_text", null ))

                            //Send Notification: Welcome Home

                            //passing string builder object to spannable string to make a section of it bold
                            val spannable = SpannableString(stringBuilder2.toString())
                            val boldSpan = StyleSpan(Typeface.BOLD)
                            val endIndex = spannable.length
                            spannable.setSpan(boldSpan, 19, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                            //Add the text to the text view
                            binding.showNotification.text = spannable

                            //Changing visibility of the views
                            binding.visibleTextview2.visibility =  View.GONE
                            binding.invisibleLayout2.visibility = View.VISIBLE
                            binding.visibleTextview3.visibility = View.GONE
                            binding.invisibleTextview2.visibility = View.VISIBLE

                            //navigate to main activity
                            moveToMain()
                        }, 6000)

                }

                //Cancel Button
                setNegativeButton("CANCEL"){ _, _ ->
                    finish()
                }

                setView(dialogLayout)
                show()
            }

        }

        if(sharedPreferences.getString("Alert_Dialog_text", null) != null){

            val stringBuilder3 = StringBuilder()
            stringBuilder3.append("Send Notification: ")
            stringBuilder3.append(sharedPreferences.getString("Alert_Dialog_text", null))

            //passing string builder object to spannable string to make a section of it bold
            val spannable = SpannableString(stringBuilder3.toString())
            val boldSpan = StyleSpan(Typeface.BOLD)
            val endIndex = spannable.length
            spannable.setSpan(boldSpan, 19, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            //Add the text to the text view
            binding.showNotification.text = spannable

            //Changing visibility of the views
            binding.visibleTextview2.visibility =  View.GONE
            binding.invisibleLayout2.visibility = View.VISIBLE
        }

    }

    private fun moveToMain(){
        val navigateToMainActivity = Intent(this@CreateRoutine, MainActivity::class.java)
        navigateToMainActivity.putExtra("Open_routine", "Open second routine fragment")
        startActivity(navigateToMainActivity)
    }




}