package com.riyaz.dakiya.core.notification

import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.model.Notification
import com.riyaz.dakiya.core.notification.Style.Companion.getStyle
import com.riyaz.dakiya.core.util.DakiyaException

internal object NotificationProcessor {

    @Throws(DakiyaException::class)
    fun process(message: RemoteMessage): NotificationCompat.Builder{
        message.data.apply {
            val notificationData = Notification(this)
            val template = notificationData.style.getStyle()
            return template.prepareBuilder(notificationData)
        }
    }
}
