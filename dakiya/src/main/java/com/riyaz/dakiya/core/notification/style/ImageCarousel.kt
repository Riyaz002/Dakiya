package com.riyaz.dakiya.core.notification.style

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Message.Companion.toBundle
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.DATA
import com.riyaz.dakiya.core.util.getImageBitmap

internal class ImageCarousel : NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channel)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setSmallIcon(message.smallIcon)
            .setAutoCancel(true)
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

            notificationEventIntent.action = NotificationEventReceiver.ACTION_BACKWARD
            val backwardIntent = PendingIntent.getBroadcast(
                Dakiya.getContext(),
                11,
                notificationEventIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            notificationEventIntent.action = NotificationEventReceiver.ACTION_FORWARD
            val forwardIntent = PendingIntent.getBroadcast(
                Dakiya.getContext(),
                11,
                notificationEventIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            carouselView.setOnClickPendingIntent(R.id.button_forward, forwardIntent)
            carouselView.setOnClickPendingIntent(R.id.button_backward, backwardIntent)
            expandedView.addView(R.id.bottom, carouselView)

            if (message.carousel.dotActiveColor != null) {
                List(message.carousel.images.size) { i ->
                    expandedView.addView(
                        R.id.ll_dots,
                        RemoteViews(Dakiya.getContext().packageName, R.layout.dot).also {
                            it.setInt(
                                R.id.dot,
                                "setBackgroundTint",
                                if (i == message.carousel.currentIndex) Color.parseColor(message.carousel.dotActiveColor) else R.color.disabled
                            )
                        }
                    )
                }
            }
        }

        message.image?.let {
            val image = getImageBitmap(it)
            expandedView.setViewVisibility(R.id.notification_image, View.VISIBLE)
            expandedView.setImageViewBitmap(R.id.notification_image, image)

            collapsedView.setViewVisibility(R.id.notification_image, View.VISIBLE)
            collapsedView.setImageViewBitmap(R.id.notification_image, image)
        }

        builder.setCustomBigContentView(expandedView)


        return builder
    }
}
