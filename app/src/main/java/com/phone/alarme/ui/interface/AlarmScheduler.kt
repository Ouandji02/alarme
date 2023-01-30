package com.phone.alarme.ui.`interface`

import com.phone.alarme.ui.model.AlarmItem

interface AlarmScheduler {
    fun scheduler(item : AlarmItem)
    fun cancel(item: AlarmItem)
}