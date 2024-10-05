package com.riyaz.dakiya.core.notification.style

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.ACTION_DELETE
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.model.Event
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.EventListener
import com.riyaz.dakiya.core.model.Timer
import com.riyaz.dakiya.core.notification.remoteview.layout.ProgressTimerExpandedView
import com.riyaz.dakiya.core.notification.remoteview.layout.TimerCollapsedView
import com.riyaz.dakiya.core.util.endsInMillis


internal class ProgressWithTimer: NotificationBuilderAssembler {
    override fun assemble(message: Message): NotificationCompat.Builder {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) throw DakiyaException.MinimumApiRequirementException(Build.VERSION_CODES.N)
        if(message.timer?.endAtString == null) throw DakiyaException.RequiredFieldNullException(Timer::endAtString.name)


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

        val endInMillis = endsInMillis(message.timer.endAtString)
        builder.setTimeoutAfter(endInMillis)

        val collapsedView = TimerCollapsedView(endInMillis).get(message)
        builder.setCustomContentView(collapsedView)

        val expandedView = ProgressTimerExpandedView(endInMillis).get(message)
        builder.setCustomBigContentView(expandedView)

        EventListener.register(Event.ScheduleUpdateNotificationJob(message))
        return builder
    }

}
