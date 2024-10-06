package com.riyaz.dakiya.core.notification


import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.Dakiya.getContext
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.DakiyaException
import com.riyaz.dakiya.core.model.Message.Companion.toBundle
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.ACTION_DELETE
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.DATA


open class NotificationBuilderAssembler {
    @Throws(DakiyaException::class)
    @CallSuper
    open fun assemble(message: Message): NotificationCompat.Builder{
        val builder = NotificationCompat.Builder(getContext(), message.channelID)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setAutoCancel(true)
            .setSmallIcon(Dakiya.smallIcon)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())

        val notificationDeleteIntent = Intent(getContext(), NotificationEventReceiver::class.java).also {
            it.action = ACTION_DELETE
            it.putExtra(DATA, message.toBundle() as Bundle)
        }
        val deleteJobIntent = PendingIntent.getBroadcast(
            getContext(),
            message.id,
            notificationDeleteIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.setDeleteIntent(deleteJobIntent)
        return builder
    }
}
