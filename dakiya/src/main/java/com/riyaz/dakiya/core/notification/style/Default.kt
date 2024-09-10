package com.riyaz.dakiya.core.notification.style

import android.content.Context
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.util.performApiLevelConfiguration


internal class Default(private val context: Context): NotificationBuilderAssembler {
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
        builder.setCustomBigContentView(expandedView)

        return builder
    }
}
