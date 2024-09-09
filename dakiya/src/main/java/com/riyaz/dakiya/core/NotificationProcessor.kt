package com.riyaz.dakiya.core

import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.model.Notification
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.getNotificationManager
import com.riyaz.dakiya.core.util.getOrNull
import com.riyaz.dakiya.core.util.getTemplate
import com.riyaz.dakiya.core.util.template.Default

internal object NotificationProcessor {

    @Throws(DakiyaException::class)
    fun process(message: RemoteMessage): NotificationCompat.Builder{
        message.data.apply {
            val notificationData = Notification(this)
            val template = notificationData.template.getTemplate()
            return template.prepareBuilder(notificationData)
        }
    }
}
