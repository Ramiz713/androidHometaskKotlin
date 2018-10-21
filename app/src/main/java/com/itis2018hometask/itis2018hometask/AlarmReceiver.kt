package com.itis2018hometask.itis2018hometask

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val wakeIntent = Intent(context, WakeUpActivity::class.java)
        wakeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pIntent = PendingIntent.getActivity(context, 0, wakeIntent, 0)
        NotificationProvider.provideNotification(context, pIntent)
    }
}
