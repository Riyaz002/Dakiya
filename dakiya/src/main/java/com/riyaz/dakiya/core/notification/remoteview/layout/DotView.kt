package com.riyaz.dakiya.core.notification.remoteview.layout

import android.widget.RemoteViews
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.remoteview.RemoteView

class DotView(val color: Int): RemoteView {
    override fun get(message: Message): RemoteViews {
        val view = RemoteViews(Dakiya.getContext().packageName, R.layout.dot)
        view.setInt(R.id.dot, "setBackgroundColor", color)
        return view
    }
}