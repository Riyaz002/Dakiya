package com.riyaz.dakiya.core.notification.remoteview.layout

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Message.Companion.toBundle
import com.riyaz.dakiya.core.notification.remoteview.View
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.DATA
import com.riyaz.dakiya.core.util.getImageBitmap

class ImageCarouselView: View {
    override fun get(message: Message): RemoteViews? {
        return if (message.carousel?.images != null) {
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
            carouselView
        } else null
    }
}