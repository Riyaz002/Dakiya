package com.riyaz.dakiya.core.notification.style

import android.content.Context
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Notification
import com.riyaz.dakiya.core.notification.NotificationStyle
import com.riyaz.dakiya.core.util.performApiLevelConfiguration


internal class Default(private val context: Context): NotificationStyle {
    override fun prepareBuilder(data: Notification): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(context, data.channel)
            .setContentTitle(data.title)
            .setContentText(data.subtitle)
            .setAutoCancel(true)

        val collapsedView = RemoteViews(context.packageName, R.layout.default_collapsed)
        collapsedView.performApiLevelConfiguration()
        collapsedView.setTextViewText(R.id.notification_title, data.title)
        collapsedView.setTextViewText(R.id.notification_body, data.subtitle)
        builder.setCustomContentView(collapsedView)

        val expandedView = RemoteViews(context.packageName, R.layout.default_expanded)
        expandedView.performApiLevelConfiguration()
        expandedView.setTextViewText(R.id.notification_title, data.title)
        expandedView.setTextViewText(R.id.notification_body, data.subtitle)
        builder.setCustomBigContentView(expandedView)

        return builder
    }
}
