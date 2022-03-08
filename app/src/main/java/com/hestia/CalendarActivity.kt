package com.hestia

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CalendarActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val mPickTimeBtn = findViewById<Button>(R.id.pickDateBtn)
        val textView     = findViewById<TextView>(R.id.dateTv)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mPickTimeBtn.setOnClickListener {

            val dpd = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                // Display Selected date in TextView
                textView.text = "" + dayOfMonth + " " + (month+1) + ", " + year
            }, year, month, day)
            dpd.show()

        }

        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
        supportActionBar!!.title = "Calendar";

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}

