package com.riyaz.dakiya.core.notification.style

import android.app.PendingIntent
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.ACTION_DELETE
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Event
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.EventListener
import com.riyaz.dakiya.core.model.Timer
import com.riyaz.dakiya.core.util.endsInMillis
import com.riyaz.dakiya.core.util.lightenColor
import java.util.Date


internal class ProgressWithTimer: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) throw DakiyaException.MinimumApiRequirementException(Build.VERSION_CODES.N)
        if(message.timer?.endAtString == null) throw DakiyaException.RequiredFieldNullException(Timer::endAtString.name)

        val endInMillis = endsInMillis(message.timer.endAtString)
        val startEpoch = message.timer.startedAt
        val currentEpoch = Date().time
        val endEpoch = currentEpoch + endInMillis

        if (currentEpoch > endEpoch) throw DakiyaException.BuildAfterTimerEndException()

        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channelID)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setWhen(message.timer.startedAt)
            .setAutoCancel(true)
            .setSmallIcon(Dakiya.smallIcon)
            .setOnlyAlertOnce(true)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        val notificationDeleteIntent = Intent(Dakiya.getContext(), NotificationEventReceiver::class.java).also {
            it.action = ACTION_DELETE
            it.putExtra(Message.ID, message.id)
        }
        val deleteJobIntent = PendingIntent.getBroadcast(
            Dakiya.getContext(),
            NotificationEventReceiver.REQUEST_CODE,
            notificationDeleteIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.setDeleteIntent(deleteJobIntent)


        builder.setTimeoutAfter(endInMillis)


        var currentPerc = ((currentEpoch - startEpoch).toDouble() / (endEpoch - startEpoch).toDouble()) * 100L

        if (currentPerc.toInt() == 0) {
            currentPerc = 3.0
        }


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

        val expandedView =
            RemoteViews(Dakiya.getContext().packageName, R.layout.progress_timer_expanded)
        expandedView.setTextViewText(R.id.notification_title, message.title)
        expandedView.setTextViewText(R.id.notification_subtitle, message.subtitle)
        expandedView.setChronometerCountDown(R.id.notification_timer, true)
        expandedView.setChronometer(
            R.id.notification_timer,
            (SystemClock.elapsedRealtime() + endInMillis),
            null,
            true
        )
        expandedView.setProgressBar(R.id.notification_progress, 100, currentPerc.toInt(), false)


        kotlin.runCatching {
            val themeColor = Color.parseColor(message.themeColor)
            collapsedView.setInt(R.id.notification_timer, "setTextColor", themeColor)
            expandedView.setInt(R.id.notification_timer, "setTextColor", themeColor)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
                val colors = intArrayOf(themeColor, lightenColor(themeColor, 0.2f))
                val colorStateList = ColorStateList(states, colors)
                expandedView.setColorStateList(
                    R.id.notification_progress,
                    "setProgressTintList",
                    colorStateList
                )
                expandedView.setColorStateList(
                    R.id.notification_progress,
                    "setProgressBackgroundTintList",
                    colorStateList
                )
            }

        }

        builder.setCustomContentView(collapsedView)
        builder.setCustomBigContentView(expandedView)

        EventListener.register(Event.ScheduleUpdateNotificationJob(message))
        return builder
    }

}
