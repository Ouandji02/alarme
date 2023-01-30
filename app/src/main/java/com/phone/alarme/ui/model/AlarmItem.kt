package com.phone.alarme.ui.model

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message : String
)