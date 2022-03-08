package com.hestia

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TimerActivity : AppCompatActivity() {
    private lateinit var tvAlarmPrompt: TextView
    private var ALARM_REQUEST_CODE: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        init()

        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
        supportActionBar!!.title = "Timer";

    }

    private fun init() {
        tvAlarmPrompt = findViewById(R.id.tv_alarm_prompt)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun clickSetAlarm(view: View) {
        tvAlarmPrompt.text = ""
        openTimePickerDialog()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            val calNow = Calendar.getInstance()

            val calSet = Calendar.getInstance()
            calSet.apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            when {
                calSet <= calNow -> {
                    // Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1)
                }
                calSet > calNow -> {
                }
                else -> {

                }
            }
            setAlarm(calSet)
        }
        val timePickerDialog = TimePickerDialog(
            this, timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE), true
        )
        timePickerDialog.setTitle("Set Alarm Time")
        timePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "UnspecifiedImmutableFlag")
    private fun setAlarm(targetCal: Calendar) {
        tvAlarmPrompt.text = """
                ***
                Alarm set on ${targetCal.time}
                ***
                """.trimIndent()
        val intent = Intent(this@TimerActivity, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(this@TimerActivity, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            targetCal.timeInMillis,
            pendingIntent
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}
