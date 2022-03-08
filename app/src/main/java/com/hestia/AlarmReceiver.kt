package com.hestia

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat


class AlarmReceiver : BroadcastReceiver() {
    private var alarmNotificationManager: NotificationManager? = null
    private var NOTIFICATION_CHANNEL_ID = "Chann_ID"
    private var NOTIFICATION_CHANNEL_NAME = "Chann_NAME"
    private val NOTIFICATION_ID = 1

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        playAudio(context)
        sendNotification(context)
    }

    private fun playAudio(context: Context) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
        mediaPlayer.start()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(context: Context) {
        val notifTitle = "This is a Notification"
        val notifContent = "Hello Friends"
        alarmNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val newIntent = Intent(context, TimerActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            context, 0,
            newIntent,PendingIntent.FLAG_IMMUTABLE
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance
            )
            alarmNotificationManager!!.createNotificationChannel(mChannel)
        }


        val inboxStyle = NotificationCompat.BigTextStyle().bigText(notifContent)
        val notifBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        notifBuilder.setContentTitle(notifTitle)
        notifBuilder.setSmallIcon(android.R.mipmap.sym_def_app_icon)
        notifBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        notifBuilder.setContentText(notifContent)
        notifBuilder.setAutoCancel(true)
        notifBuilder.setStyle(inboxStyle)
        notifBuilder.setContentIntent(contentIntent)

        alarmNotificationManager!!.notify(NOTIFICATION_ID, notifBuilder.build())
    }
}