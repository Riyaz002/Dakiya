package com.riyaz.dakiya.core.notification.remoteview.layout

import android.widget.RemoteViews
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.ImageLoader
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.remoteview.RemoteView

class DefaultCollapsedView: RemoteView {
    override fun get(message: Message): RemoteViews {
        val view = RemoteViews(Dakiya.getContext().packageName, R.layout.default_collapsed)
        view.setTextViewText(R.id.notification_title, message.title)
        view.setTextViewText(R.id.notification_body, message.subtitle)
        message.image?.let {
            val image = ImageLoader.getImageBitmap(it)
            view.setViewVisibility(R.id.notification_image, android.view.View.VISIBLE)
            view.setImageViewBitmap(R.id.notification_image, image)
        }
        return view
    }
}