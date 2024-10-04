package com.riyaz.dakiya.core.notification.style

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Carousel
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Message.Companion.toBundle
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.DATA
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.getImageBitmap

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

        val collapsedView = RemoteViews(Dakiya.getContext().packageName, R.layout.default_collapsed)
        collapsedView.setTextViewText(R.id.notification_title, message.title)
        collapsedView.setTextViewText(R.id.notification_body, message.subtitle)
        builder.setCustomContentView(collapsedView)

        val expandedView = RemoteViews(Dakiya.getContext().packageName, R.layout.default_expanded)
        expandedView.setTextViewText(R.id.notification_title, message.title)
        expandedView.setTextViewText(R.id.notification_body, message.subtitle)

        if (message.carousel?.images != null) {
            val carouselView = RemoteViews(Dakiya.getContext().packageName, R.layout.image_carousel)
            val bitmap = getImageBitmap(message.carousel.images[message.carousel.currentIndex])
            carouselView.setImageViewBitmap(R.id.notification_carousel, bitmap)
            val notificationEventIntent = Intent(Dakiya.getContext(), NotificationEventReceiver::class.java)
            notificationEventIntent.putExtra(DATA, message.toBundle() as Bundle)
            var backwardIntent: PendingIntent? = null
            var forwardIntent: PendingIntent? = null
            if(message.carousel.currentIndex > 0){
                notificationEventIntent.action = NotificationEventReceiver.ACTION_BACKWARD
                backwardIntent = PendingIntent.getBroadcast(
                    Dakiya.getContext(),
                    11,
                    notificationEventIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
                carouselView.setFloat(R.id.button_backward, "setAlpha", 1f)
            } else carouselView.setFloat(R.id.button_backward, "setAlpha", 0.1f)
            carouselView.setOnClickPendingIntent(R.id.button_backward, backwardIntent)

            if(message.carousel.currentIndex < message.carousel.images.size-1){
                notificationEventIntent.action = NotificationEventReceiver.ACTION_FORWARD
                forwardIntent = PendingIntent.getBroadcast(
                    Dakiya.getContext(),
                    11,
                    notificationEventIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
                carouselView.setFloat(R.id.button_forward, "setAlpha", 1f)
            } else carouselView.setFloat(R.id.button_forward, "setAlpha", 0.1f)
            carouselView.setOnClickPendingIntent(R.id.button_forward, forwardIntent)
            expandedView.addView(R.id.bottom, carouselView)
        }

        builder.setCustomBigContentView(expandedView)


        return builder
    }
}
