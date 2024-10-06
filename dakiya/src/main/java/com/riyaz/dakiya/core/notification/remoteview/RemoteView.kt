package com.riyaz.dakiya.core.notification.remoteview

import android.widget.RemoteViews
import com.riyaz.dakiya.core.model.Message

fun interface RemoteView {
    fun get(message: Message): RemoteViews?
}
