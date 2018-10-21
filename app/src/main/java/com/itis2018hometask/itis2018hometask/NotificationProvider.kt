package com.itis2018hometask.itis2018hometask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat

class NotificationProvider {

    companion object {
        val CHANNEL_ID = "alarm1"
        fun provideNotification(context: Context, pInent: PendingIntent) {
            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.channel_name)
                val descriptionText = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
                mChannel.description = descriptionText
                notificationManager.createNotificationChannel(mChannel)
            }

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), AudioAttributes.USAGE_ALARM)
                    .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                    .setContentTitle("Пора вставать!")
                    .setContentText("Доброе утро!\nНовый день ждет вас!(нет)")
                    .setContentIntent(pInent)
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.BigTextStyle())
                    .build()
            notificationManager.notify(1, notification)
        }
    }
}
