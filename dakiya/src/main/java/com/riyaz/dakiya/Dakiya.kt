package com.riyaz.dakiya

import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.Constant.DAKIYA
import com.riyaz.dakiya.core.notification.NotificationProcessor
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

    fun processNotification(message: RemoteMessage): NotificationCompat.Builder {
        return NotificationProcessor.process(message)
    }
}