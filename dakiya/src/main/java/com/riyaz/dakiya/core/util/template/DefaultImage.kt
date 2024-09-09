package com.riyaz.dakiya.core.util.template

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Notification
import com.riyaz.dakiya.core.util.Event
import com.riyaz.dakiya.core.util.EventListener
import com.riyaz.dakiya.core.util.getImageBitmap
import com.riyaz.dakiya.core.util.isBuildVersionSmallerThan
import com.riyaz.dakiya.core.util.performApiLevelConfiguration


class DefaultImage(private val context: Context): NotificationTemplate() {
    override fun prepareBuilder(data: Notification): NotificationCompat.Builder {
        EventListener.emit(Event.Build(context, data.channel))
        val builder = NotificationCompat.Builder(context, data.channel)
            .setContentTitle(data.title)
            .setContentText(data.subtitle)
            .setAutoCancel(true)

        val collapsedView = RemoteViews(context.packageName, R.layout.default_collapsed)
        collapsedView.performApiLevelConfiguration(data)
        collapsedView.setTextViewText(R.id.notification_title, data.title)
        collapsedView.setTextViewText(R.id.notification_body, data.subtitle)
        builder.setCustomContentView(collapsedView)

        val expandedView = RemoteViews(context.packageName, R.layout.default_expanded)
        expandedView.performApiLevelConfiguration(data)
        if(isBuildVersionSmallerThan(Build.VERSION_CODES.N_MR1)) expandedView.setViewVisibility(R.id.ll_notification_header, View.VISIBLE)
        expandedView.setTextViewText(R.id.notification_title, data.title)
        expandedView.setTextViewText(R.id.notification_body, data.subtitle)

        data.image?.let {
            val image = getImageBitmap(it)
            expandedView.setViewVisibility(R.id.notification_image, View.VISIBLE)
            expandedView.setImageViewBitmap(R.id.notification_image, image)
        }

        builder.setCustomBigContentView(expandedView)


        return builder
    }
}
