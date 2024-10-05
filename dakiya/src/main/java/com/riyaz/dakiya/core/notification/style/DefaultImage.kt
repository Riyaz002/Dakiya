package com.riyaz.dakiya.core.notification.style

import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultCollapsedView
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultExpandedView

internal class DefaultImage: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channelID)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setAutoCancel(true)
            .setSmallIcon(Dakiya.smallIcon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        val collapsedView = DefaultCollapsedView().get(message)
        val expandedView = DefaultExpandedView().get(message)



        builder.setCustomContentView(collapsedView)
        builder.setCustomBigContentView(expandedView)


        return builder
    }
}
