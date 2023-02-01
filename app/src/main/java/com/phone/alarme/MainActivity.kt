package com.phone.alarme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phone.alarme.ui.impl.AndroidAlarmScheduler
import com.phone.alarme.ui.model.AlarmItem
import com.phone.alarme.ui.theme.AlarmeTheme
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotification(this)
        setContent {
            AlarmeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Screen(context = this)
                }
            }
        }
    }
}

@Composable
fun Screen(context: Context) {
    val androidAlarmScheduler = AndroidAlarmScheduler(context)
    var calendar = Calendar.getInstance()
    var timeText by remember { mutableStateOf("00:00") }
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val time = if (timeText != "00:00") LocalTime.parse(timeText, formatter) else LocalTime.now()
    val dialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            timeText = (LocalTime.of(hour, minute).toString())
            hours = hour
            minutes = minute-1
        },
        time.hour,
        time.minute,
        true,
    )
    calendar[Calendar.HOUR_OF_DAY] = hours
    calendar[Calendar.MINUTE] = minutes
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = timeText.toString(), style = MaterialTheme.typography.h4)
        Button(
            onClick = { dialog.show() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text("choisir l'heure")
        }
        Button(
            onClick = {
                val alarmItem = AlarmItem(
                    time = calendar.timeInMillis,
                    message = "Launch alarm"
                )
                alarmItem.let(androidAlarmScheduler::scheduler)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text("Activer l'alarme")
        }
        Button(
            onClick = {
                val alarmItem = AlarmItem(
                    time = calendar.timeInMillis,
                    message = "Launch alarm"
                )
                alarmItem.hashCode().let(androidAlarmScheduler::cancel)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text("Annuler l'alarme")
        }
    }
}



fun createNotification(context: Context) {
    val name: CharSequence = "androidinfinix"
    val description = "channel for alarm"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val channel = NotificationChannel("android", name, importance)
    channel.description = description
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    notificationManager.createNotificationChannel(channel)
}