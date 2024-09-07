package com.riyaz.dakiya.core.util.template

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.BigPictureStyle
import com.riyaz.dakiya.core.model.Notification
import com.riyaz.dakiya.core.util.Event
import com.riyaz.dakiya.core.util.EventListener
import com.riyaz.dakiya.core.util.getImageBitmap


class Default(private val context: Context): NotificationTemplate() {
    override fun build(data: Notification): android.app.Notification {
        EventListener.emit(Event.Build(context, data.channel))
        val builder = NotificationCompat.Builder(context, data.channel)
            .setContentTitle(data.title)
            .setContentText(data.subtitle)

        if(data.image!=null){
            val style = BigPictureStyle().bigPicture(getImageBitmap(data.image))
            builder.setStyle(style)
        }
        return builder.build()
    }
}