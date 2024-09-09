package com.riyaz.dakiya.core.util.template

import android.content.Context
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Notification
import com.riyaz.dakiya.core.util.Event
import com.riyaz.dakiya.core.util.EventListener


class Default(private val context: Context): NotificationTemplate() {
    override fun prepareBuilder(data: Notification): NotificationCompat.Builder {
        EventListener.emit(Event.Build(context, data.channel))
        val builder = NotificationCompat.Builder(context, data.channel)
            .setContentTitle(data.title)
            .setContentText(data.subtitle)

        val collapsedView = RemoteViews(context.packageName, R.layout.default_collapsed)
        collapsedView.setTextViewText(R.id.notification_title, data.title)
        collapsedView.setTextViewText(R.id.notification_body, data.subtitle)
        builder.setCustomContentView(collapsedView)

        val expandedView = RemoteViews(context.packageName, R.layout.default_expanded)
        expandedView.setTextViewText(R.id.notification_title, data.title)
        expandedView.setTextViewText(R.id.notification_body, data.subtitle)
        builder.setCustomBigContentView(expandedView)

        return builder
    }
}
