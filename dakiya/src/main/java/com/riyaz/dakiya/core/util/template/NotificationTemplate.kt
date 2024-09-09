package com.riyaz.dakiya.core.util.template

import androidx.core.app.NotificationCompat


abstract class NotificationTemplate {
    abstract fun prepareBuilder(data: com.riyaz.dakiya.core.model.Notification): NotificationCompat.Builder
}
