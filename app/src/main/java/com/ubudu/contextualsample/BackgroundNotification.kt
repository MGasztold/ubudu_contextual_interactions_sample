package com.ubudu.contextualsample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

object BackgroundNotification {

    private const val NOTIFICATION_CHANNEL_ID = "UbuduIL"
    private const val NOTIFICATION_CHANNEL_NAME = "Ubudu IL notifications"

    @JvmStatic
    fun getForegroundServiceNotification(context: Context): Notification {
        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager
                    = context
                    .getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.createNotificationChannel(
                    NotificationChannel(
                            NOTIFICATION_CHANNEL_ID,
                            NOTIFICATION_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_NONE
                    )
            )
            Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
        } else {
            Notification.Builder(context)
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("Ubudu SDK background BLE scanning...")
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)
        return builder.build()
    }

    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteNotificationChannel(context: Context) {
        val notificationManager
                = context
                .getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        notificationManager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID)
    }

}