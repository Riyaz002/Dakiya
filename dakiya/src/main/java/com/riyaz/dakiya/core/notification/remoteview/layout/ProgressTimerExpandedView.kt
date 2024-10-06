package com.riyaz.dakiya.core.notification.remoteview.layout

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Timer
import com.riyaz.dakiya.core.notification.remoteview.RemoteView
import com.riyaz.dakiya.core.DakiyaException
import com.riyaz.dakiya.core.lightenColor
import java.util.Date

class ProgressTimerExpandedView(private val endInMillis: Long): RemoteView {

    @Throws(DakiyaException::class)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun get(message: Message): RemoteViews {
        if(message.timer?.endAtString == null) throw DakiyaException.RequiredFieldNullException(Timer::endAtString.name)
        val startEpoch = message.timer.startedAt
        val currentEpoch = Date().time
        val endEpoch = currentEpoch + endInMillis
        if (currentEpoch > endEpoch) throw DakiyaException.BuildAfterTimerEndException()
        var currentPerc = ((currentEpoch - startEpoch).toDouble() / (endEpoch - startEpoch).toDouble()) * 100L

        if (currentPerc.toInt() == 0) {
            currentPerc = 3.0
        }

        val view = RemoteViews(Dakiya.getContext().packageName, R.layout.progress_timer_expanded)
        view.setTextViewText(R.id.notification_title, message.title)
        view.setTextViewText(R.id.notification_body, message.subtitle)
        view.setChronometerCountDown(R.id.notification_timer, true)
        view.setChronometer(
            R.id.notification_timer,
            (SystemClock.elapsedRealtime() + endInMillis),
            null,
            true
        )
        view.setProgressBar(R.id.notification_progress, 100, currentPerc.toInt(), false)
        view.setInt(R.id.notification_body, "setMaxLines",10)
        kotlin.runCatching {
            val themeColor = Color.parseColor(message.themeColor)
            view.setInt(R.id.notification_timer, "setTextColor", themeColor)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
                val colors = intArrayOf(themeColor, lightenColor(themeColor, 0.2f))
                val colorStateList = ColorStateList(states, colors)
                view.setColorStateList(
                    R.id.notification_progress,
                    "setProgressTintList",
                    colorStateList
                )
                view.setColorStateList(
                    R.id.notification_progress,
                    "setProgressBackgroundTintList",
                    colorStateList
                )
            }

        }
        return view
    }
}