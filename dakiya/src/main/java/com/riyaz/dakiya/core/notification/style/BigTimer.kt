package com.riyaz.dakiya.core.notification.style

import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.endsInMillis
import java.util.Date


internal class BigTimer: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) throw DakiyaException.MinimumApiRequirementException(Build.VERSION_CODES.N)
        if(message.timer?.endAtString == null) throw DakiyaException.RequiredFieldNullException(Message::timer.name)

        val endInMillis = endsInMillis(message.timer.endAtString)
        val currentEpoch = Date().time
        val endEpoch = currentEpoch + endInMillis

        if (currentEpoch > endEpoch) throw DakiyaException.BuildAfterTimerEndException()

        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channelID)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setWhen(message.timer.startedAt)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setTimeoutAfter(endInMillis)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())


        val collapsedView = RemoteViews(Dakiya.getContext().packageName, R.layout.timer_collapsed)
        collapsedView.setTextViewText(R.id.notification_title, message.title)
        collapsedView.setTextViewText(R.id.notification_subtitle, message.subtitle)
        collapsedView.setChronometerCountDown(R.id.notification_timer, true)
        collapsedView.setChronometer(
            R.id.notification_timer,
            (SystemClock.elapsedRealtime() + endInMillis),
            null,
            true
        )

        val expandedView = RemoteViews(Dakiya.getContext().packageName, R.layout.big_timer_expanded)
        expandedView.setTextViewText(R.id.notification_title, message.title)
        expandedView.setTextViewText(R.id.notification_subtitle, message.subtitle)
        expandedView.setChronometerCountDown(R.id.notification_timer, true)
        expandedView.setChronometer(
            R.id.notification_timer,
            (SystemClock.elapsedRealtime() + endInMillis),
            null,
            true
        )


        kotlin.runCatching {
            val themeColor = Color.parseColor(message.themeColor)
            collapsedView.setInt(R.id.notification_timer, "setTextColor", themeColor)
            expandedView.setInt(R.id.notification_timer, "setTextColor", themeColor)
        }

        builder.setCustomContentView(collapsedView)
        builder.setCustomBigContentView(expandedView)
        return builder
    }
}
