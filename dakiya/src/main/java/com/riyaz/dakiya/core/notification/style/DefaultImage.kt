package com.riyaz.dakiya.core.notification.style

import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultCollapsedView
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultImageExpandedView
import com.riyaz.dakiya.core.DakiyaException

internal class DefaultImage: NotificationBuilderAssembler() {
    override fun assemble(message: Message): NotificationCompat.Builder {
        if(message.image==null) throw DakiyaException.RequiredFieldNullException(Message::image.name)
        val builder = super.assemble(message)

        val collapsedView = DefaultCollapsedView().get(message)
        builder.setCustomContentView(collapsedView)

        val expandedView = DefaultImageExpandedView().get(message)
        builder.setCustomBigContentView(expandedView)

        return builder
    }
}
