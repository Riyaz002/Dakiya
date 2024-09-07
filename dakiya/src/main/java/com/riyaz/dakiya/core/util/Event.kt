package com.riyaz.dakiya.core.util

import android.app.NotificationManager
import android.content.Context

sealed interface Event {
    data class Build(val context: Context, val channelId: String) : Event
}