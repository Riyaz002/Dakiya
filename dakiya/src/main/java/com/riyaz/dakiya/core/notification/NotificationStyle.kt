package com.riyaz.dakiya.core.notification


import androidx.core.app.NotificationCompat


fun interface NotificationStyle {
    fun prepareBuilder(data: com.riyaz.dakiya.core.model.Notification): NotificationCompat.Builder
}
