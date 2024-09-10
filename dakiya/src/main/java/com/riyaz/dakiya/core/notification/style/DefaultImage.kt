package com.riyaz.dakiya.core.notification.style

import android.content.Context
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.util.getImageBitmap
import com.riyaz.dakiya.core.util.performApiLevelConfiguration


internal class DefaultImage(private val context: Context): NotificationBuilderAssembler {
    override fun assembleBuilder(message: Message): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, message.channel)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setAutoCancel(true)

        val collapsedView = RemoteViews(context.packageName, R.layout.default_collapsed)
        collapsedView.performApiLevelConfiguration()
        collapsedView.setTextViewText(R.id.notification_title, message.title)
        collapsedView.setTextViewText(R.id.notification_body, message.subtitle)
        builder.setCustomContentView(collapsedView)

        val expandedView = RemoteViews(context.packageName, R.layout.default_expanded)
        expandedView.performApiLevelConfiguration()
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
        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())


        return builder
    }
}
