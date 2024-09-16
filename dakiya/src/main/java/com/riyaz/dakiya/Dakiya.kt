package com.riyaz.dakiya

import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.Constant.DAKIYA
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.getNotificationManager
import com.riyaz.dakiya.core.util.getOrNull
import java.lang.ref.WeakReference

object Dakiya {

    private var weakContextRef: WeakReference<Context>? = null
    private val applicationContext: Context
        get() = weakContextRef!!.get()!!
    fun getContext() = applicationContext
    fun init(context: Context){
        weakContextRef = WeakReference(context.applicationContext)
    }

    fun isDakiyaNotification(message: RemoteMessage): Boolean{
        return message.data.getOrNull(DAKIYA)?.toBoolean() == true
    }

    /**
     * Use this to get [NotificationCompat.Builder] with configurations according to input [Message]
     * Helpful in case you want to perform extra configuration for the notification
     */
    fun prepareNotificationBuilder(message: Message): Result<NotificationCompat.Builder> {
        try{
            val assembler = message.style.getAssembler()
            return Result.success(assembler.assemble(message))
        } catch (e: DakiyaException){
            return Result.failure(e)
        }
    }

    fun showNotification(message: Message){
        val result = prepareNotificationBuilder(message)
        result.getOrNull()?.let { notificationBuilder ->
            getNotificationManager()?.notify(message.id, notificationBuilder.build())
        }
    }
}