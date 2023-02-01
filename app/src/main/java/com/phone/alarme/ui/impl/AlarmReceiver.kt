package com.phone.alarme.ui.impl

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.phone.alarme.MainActivity2

class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        println("TRIGGER ALARM: $message")

        context!!.startService(Intent(context, MyServices::class.java))
        val i = Intent(context, MainActivity2::class.java).apply {
            putExtra("ID_ALARM",message)
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, i, 0)
        val builder = NotificationCompat.Builder(context!!, "android")
            .setContentTitle("Alarme de la infinix")
            .setContentText("Thierry faut te reveiller")
            .setSmallIcon(androidx.core.R.drawable.notification_bg_low_normal)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())
    }
}