package com.itis2018hometask.itis2018hometask

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        time_picker.setIs24HourView(true)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(this, AlarmReceiver::class.java)
        val calendar = Calendar.getInstance()
        val pIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)

        btn_start.setOnClickListener {
            calendar.set(Calendar.HOUR_OF_DAY, time_picker.hour)
            calendar.set(Calendar.MINUTE, time_picker.minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            if (calendar.timeInMillis <= System.currentTimeMillis())
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1)
            val time = AlarmManager.AlarmClockInfo(calendar.timeInMillis, pIntent)
            alarmManager.setAlarmClock(time, pIntent)

            val hour = if (time_picker.hour < 10) "0${time_picker.hour}"
            else time_picker.hour.toString()
            val minute = if (time_picker.minute < 10) "0${time_picker.minute}"
            else time_picker.minute.toString()
            tv_time.text = "$hour:$minute"
            tv_time.setTextColor(Color.parseColor("#ffffff"))
            Toast.makeText(this, "Будильник включен!", Toast.LENGTH_SHORT).show()
        }

        btn_cancel.setOnClickListener {
            tv_time.setTextColor(Color.parseColor("#bdbdbd"))
            Toast.makeText(this, "Будильник выключен!", Toast.LENGTH_SHORT).show()
            alarmManager.cancel(PendingIntent.getBroadcast(this, 0, myIntent, 0))
        }
    }
}
