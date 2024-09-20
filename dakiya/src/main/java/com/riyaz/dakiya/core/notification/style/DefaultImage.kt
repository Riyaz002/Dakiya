package com.riyaz.dakiya.core.notification.style

import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.util.getImageBitmap


internal class DefaultImage: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channel)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        val collapsedView = RemoteViews(Dakiya.getContext().packageName, R.layout.default_collapsed)
        collapsedView.setTextViewText(R.id.notification_title, message.title)
        collapsedView.setTextViewText(R.id.notification_body, message.subtitle)
        builder.setCustomContentView(collapsedView)

        val expandedView = RemoteViews(Dakiya.getContext().packageName, R.layout.default_expanded)
        expandedView.setTextViewText(R.id.notification_title, message.title)
        expandedView.setTextViewText(R.id.notification_body, message.subtitle)

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
