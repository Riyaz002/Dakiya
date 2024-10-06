package com.riyaz.dakiya.core.notification.style

import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultCollapsedView
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultExpandedView


internal class Default: NotificationBuilderAssembler() {
    override fun assemble(message: Message): NotificationCompat.Builder {
        val builder = super.assemble(message)

        builder.setCustomContentView(DefaultCollapsedView().get(message))
        builder.setCustomBigContentView(DefaultExpandedView().get(message))

        return builder
    }
}
