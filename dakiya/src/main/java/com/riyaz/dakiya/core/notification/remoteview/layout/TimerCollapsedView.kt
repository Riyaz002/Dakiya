package com.riyaz.dakiya.core.notification.remoteview.layout

import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.remoteview.View

class TimerCollapsedView(private val endInMillis: Long): View {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun get(message: Message): RemoteViews {
        val view = RemoteViews(Dakiya.getContext().packageName, R.layout.timer_collapsed)
        view.setTextViewText(R.id.notification_title, message.title)
        view.setTextViewText(R.id.notification_subtitle, message.subtitle)
        view.setChronometerCountDown(R.id.notification_timer, true)
        view.setChronometer(
            R.id.notification_timer,
            (SystemClock.elapsedRealtime() + endInMillis),
            null,
            true
        )
        kotlin.runCatching {
            val themeColor = Color.parseColor(message.themeColor)
            view.setInt(R.id.notification_timer, "setTextColor", themeColor)
        }
        return view
    }
}