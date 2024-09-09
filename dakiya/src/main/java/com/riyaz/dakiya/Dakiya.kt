package com.riyaz.dakiya

import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.Constant.DAKIYA
import com.riyaz.dakiya.core.NotificationProcessor
import com.riyaz.dakiya.core.util.getOrNull

object Dakiya {

    private lateinit var applicationContext: Context

    fun getContext() = applicationContext

    fun init(context: Context){
        applicationContext = context.applicationContext
    }

    fun isDakiyaNotification(message: RemoteMessage): Boolean{
        return message.data.getOrNull(DAKIYA)?.toBoolean() == true
    }

    fun processNotification(message: RemoteMessage): NotificationCompat.Builder {
        return NotificationProcessor.process(message)
    }
}