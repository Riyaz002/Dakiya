package com.riyaz.dakiya.core.notification.style

import android.os.Build
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.notification.remoteview.layout.BigTimerExpandedView
import com.riyaz.dakiya.core.notification.remoteview.layout.TimerCollapsedView
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
            .setSmallIcon(Dakiya.smallIcon)
            .setTimeoutAfter(endInMillis)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        builder.setCustomContentView(TimerCollapsedView(endInMillis).get(message))
        builder.setCustomBigContentView(BigTimerExpandedView(endInMillis).get(message))
        return builder
    }
}
