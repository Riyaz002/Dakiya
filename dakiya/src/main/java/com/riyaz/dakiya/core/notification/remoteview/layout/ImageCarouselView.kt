package com.riyaz.dakiya.core.notification.remoteview.layout

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.RemoteViews
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.ImageLoader
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Message.Companion.toBundle
import com.riyaz.dakiya.core.notification.remoteview.RemoteView
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.DATA

class ImageCarouselView: RemoteView {
    override fun get(message: Message): RemoteViews? {
        return if (message.carousel?.images != null) {
            val carouselView = RemoteViews(Dakiya.getContext().packageName, R.layout.image_carousel)
            val bitmap = ImageLoader.getImageBitmap(message.carousel.images[message.carousel.currentIndex])
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
                carouselView.setInt(R.id.button_backward, "setImageAlpha", 255)
            } else carouselView.setInt(R.id.button_backward, "setImageAlpha", 50)
            carouselView.setOnClickPendingIntent(R.id.button_backward, backwardIntent)

            if(message.carousel.currentIndex < message.carousel.images.size-1){
                notificationEventIntent.action = NotificationEventReceiver.ACTION_FORWARD
                forwardIntent = PendingIntent.getBroadcast(
                    Dakiya.getContext(),
                    11,
                    notificationEventIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
                carouselView.setInt(R.id.button_forward, "setImageAlpha", 255)
            } else carouselView.setInt(R.id.button_forward, "setImageAlpha", 50)
            carouselView.setOnClickPendingIntent(R.id.button_forward, forwardIntent)
            carouselView.setViewVisibility(R.id.dots, android.view.View.VISIBLE)
            if(message.carousel.dotActiveColor!=null) {
                val activeDotColor = Color.parseColor(message.carousel.dotActiveColor)
                val inactiveColor = Color.argb(150, Color.red(activeDotColor), Color.green(activeDotColor), Color.blue(activeDotColor))
                repeat(message.carousel.images.size){
                    val dotColor = if(message.carousel.currentIndex == it) activeDotColor else inactiveColor
                    val dot = DotView(dotColor).get(message)
                    carouselView.addView(R.id.dots, dot)
                }
            }
            carouselView
        } else null
    }
}