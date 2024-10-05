package com.riyaz.dakiya.core.notification.style

import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultCollapsedView
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultExpandedView


internal class Default: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channelID)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setAutoCancel(true)
            .setSmallIcon(Dakiya.smallIcon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        builder.setCustomContentView(DefaultCollapsedView().get(message))
        builder.setCustomBigContentView(DefaultExpandedView().get(message))

        return builder
    }
}
