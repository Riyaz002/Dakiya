package com.riyaz.dakiya.core.notification.style

import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.util.performApiLevelConfiguration


internal class Default: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channel)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setSmallIcon(message.smallIcon)
            .setAutoCancel(true)

        val collapsedView = RemoteViews(Dakiya.getContext().packageName, R.layout.default_collapsed)
        collapsedView.performApiLevelConfiguration()
        collapsedView.setTextViewText(R.id.notification_title, message.title)
        collapsedView.setTextViewText(R.id.notification_body, message.subtitle)
        builder.setCustomContentView(collapsedView)

        val expandedView = RemoteViews(Dakiya.getContext().packageName, R.layout.default_expanded)
        expandedView.performApiLevelConfiguration()
        expandedView.setTextViewText(R.id.notification_title, message.title)
        expandedView.setTextViewText(R.id.notification_body, message.subtitle)
        builder.setCustomBigContentView(expandedView)

        return builder
    }
}
