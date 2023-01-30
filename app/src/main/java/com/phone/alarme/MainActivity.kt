package com.phone.alarme

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.phone.alarme.ui.impl.AndroidAlarmScheduler
import com.phone.alarme.ui.model.AlarmItem
import com.phone.alarme.ui.theme.AlarmeTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Alarm(this)
                }
            }
        }
    }
}

@Composable
fun Alarm(context: Context) {
    val androidAlarmScheduler = AndroidAlarmScheduler(context)
    var time by remember { mutableStateOf("0") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = time,
            onValueChange = { new -> time = new },
            label = { Text("Entrer le temps de declenchement") })
        TextField(
            value = message,
            onValueChange = { new -> message = new },
            label = { Text(text = "Entre le message") })
        Row() {
            Button(onClick = {
                val alarmItem = AlarmItem(
                    time = LocalDateTime.now().plusSeconds(time.toLong()),
                    message = message
                )
                alarmItem.let (androidAlarmScheduler::scheduler)
                time = ""
                message = ""
            }) {
                Text("Lancer")
            }
            Button(onClick = {
                val alarmItem = AlarmItem(
                    time = LocalDateTime.now().plusSeconds(time.toLong()),
                    message = message
                )
                alarmItem.let(androidAlarmScheduler::cancel)
            }) {
                Text("Annuler")
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlarmeTheme {
        Greeting("Android")
    }
}