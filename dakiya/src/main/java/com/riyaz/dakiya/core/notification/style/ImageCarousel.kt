package com.riyaz.dakiya.core.notification.style


import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Carousel
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultCollapsedView
import com.riyaz.dakiya.core.notification.remoteview.layout.DefaultExpandedView
import com.riyaz.dakiya.core.notification.remoteview.layout.ImageCarouselView
import com.riyaz.dakiya.core.util.DakiyaException

internal class ImageCarousel : NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        if(message.carousel?.images.isNullOrEmpty()) throw DakiyaException.RequiredFieldNullException(Carousel::images.name)
        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channelID)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setAutoCancel(true)
            .setSmallIcon(Dakiya.smallIcon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setOnlyAlertOnce(true)

        val collapsedView = DefaultCollapsedView().get(message)
        builder.setCustomContentView(collapsedView)

        val expandedView = DefaultExpandedView().get(message)
        expandedView.addView(R.id.bottom, ImageCarouselView().get(message))
        expandedView.setInt(R.id.notification_body, "setMaxLines", 7)
        builder.setCustomBigContentView(expandedView)
        return builder
    }
}
