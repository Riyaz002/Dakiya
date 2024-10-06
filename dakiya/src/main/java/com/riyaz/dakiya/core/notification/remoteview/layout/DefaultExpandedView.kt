package com.riyaz.dakiya.core.notification.remoteview.layout

import android.widget.RemoteViews
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.remoteview.RemoteView

class DefaultExpandedView: RemoteView {
    override fun get(message: Message): RemoteViews {
        val view = RemoteViews(Dakiya.getContext().packageName, R.layout.default_expanded)
        view.setTextViewText(R.id.notification_title, message.title)
        view.setTextViewText(R.id.notification_body, message.subtitle)
        view.setInt(R.id.notification_body, "setMaxLines", 12)
        return view
    }
}