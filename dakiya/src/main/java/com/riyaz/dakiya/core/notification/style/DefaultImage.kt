package com.riyaz.dakiya.core.notification.style

import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultCollapsedView
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultExpandedView
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultImageExpandedView
import com.riyaz.dakiya.core.util.DakiyaException

internal class DefaultImage: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        if(message.image==null) throw DakiyaException.RequiredFieldNullException(Message::image.name)
        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channelID)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setAutoCancel(true)
            .setSmallIcon(Dakiya.smallIcon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        val collapsedView = DefaultCollapsedView().get(message)
        builder.setCustomContentView(collapsedView)

        val expandedView = DefaultImageExpandedView().get(message)
        builder.setCustomBigContentView(expandedView)

        return builder
    }
}
