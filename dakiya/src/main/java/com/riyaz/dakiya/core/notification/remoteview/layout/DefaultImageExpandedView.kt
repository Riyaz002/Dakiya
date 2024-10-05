package com.riyaz.dakiya.core.notification.remoteview.layout

import android.widget.RemoteViews
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.remoteview.View
import com.riyaz.dakiya.core.util.getImageBitmap

class DefaultImageExpandedView: View {
    override fun get(message: Message): RemoteViews {
        val view = DefaultExpandedView().get(message)
        message.image.let {
            val image = getImageBitmap(it)
            view.setViewVisibility(R.id.notification_image, android.view.View.VISIBLE)
            view.setImageViewBitmap(R.id.notification_image, image)
            view.setInt(R.id.notification_body, "setMaxLines", 7)
        }
        return view
    }
}