package com.riyaz.dakiya.core.notification.style

import android.os.Build
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.model.Event
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.DakiyaException
import com.riyaz.dakiya.core.EventListener
import com.riyaz.dakiya.core.model.Timer
import com.riyaz.dakiya.core.notification.remoteview.layout.ProgressTimerExpandedView
import com.riyaz.dakiya.core.notification.remoteview.layout.TimerCollapsedView
import com.riyaz.dakiya.core.endsInMillis


internal class ProgressWithTimer: NotificationBuilderAssembler() {
    override fun assemble(message: Message): NotificationCompat.Builder {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) throw DakiyaException.MinimumApiRequirementException(Build.VERSION_CODES.N)
        if(message.timer?.endAtString == null) throw DakiyaException.RequiredFieldNullException(Timer::endAtString.name)


        val builder = super.assemble(message)
            .setWhen(message.timer.startedAt)
            .setOnlyAlertOnce(true)

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
