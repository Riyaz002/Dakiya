package com.riyaz.dakiya.core.notification.remoteview.layout

import android.graphics.Color
import android.widget.RemoteViews
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.remoteview.View
import com.riyaz.dakiya.core.util.getImageBitmap

class DotView(val color: String): View {
    override fun get(message: Message): RemoteViews {
        val view = RemoteViews(Dakiya.getContext().packageName, R.layout.dot)
        view.setInt(R.id.dot, "setBackground", Color.parseColor(color))
        return view
    }
}