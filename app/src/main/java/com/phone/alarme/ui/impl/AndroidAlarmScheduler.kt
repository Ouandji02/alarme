package com.phone.alarme.ui.impl

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.phone.alarme.ui.`interface`.AlarmScheduler
import com.phone.alarme.ui.model.AlarmItem

class AndroidAlarmScheduler(private val context: Context) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun scheduler(item: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", item.hashCode().toString())

        }
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            item.time,
            AlarmManager.INTERVAL_DAY,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        Toast.makeText(context, "Alarme activée", Toast.LENGTH_LONG).show()
    }

    override fun cancel(hashcode1: Int) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                hashcode1,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        context.stopService(Intent(context, MyServices::class.java))
        Toast.makeText(context, "Alarme annulée", Toast.LENGTH_LONG).show()
    }
}