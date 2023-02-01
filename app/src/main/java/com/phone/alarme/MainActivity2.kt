package com.phone.alarme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.phone.alarme.ui.impl.AndroidAlarmScheduler
import com.phone.alarme.ui.impl.MyServices
import com.phone.alarme.ui.model.AlarmItem
import com.phone.alarme.ui.theme.AlarmeTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContent {
            AlarmeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            val alarmScheduler = AndroidAlarmScheduler(context)
                           context.intent.getStringExtra("id_alarme")!!.toInt().let(alarmScheduler::cancel)
                        }) {
                            Text("Arreter l'alarme")
                        }
                    }
                }
            }
        }
    }
}
