package com.riyaz.dakiya

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.Constant.DAKIYA
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.getNotificationManager
import com.riyaz.dakiya.core.util.getOrNull
import kotlin.concurrent.thread


object Dakiya {
    private lateinit var applicationContext: Context
    fun getContext() = applicationContext
    private var _smallIcon: Int? = null
    internal val smallIcon: Int get() = _smallIcon!!

    internal fun init(context: Context){
        val ai: ApplicationInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
        val bundle = ai.metaData
        val iconId = bundle.getInt("com.riyaz.dakiya.Notification_Small_Icon")
        if(iconId == 0) throw DakiyaException.MetaTagNotFoundException("Notification_Small_Icon")
        else _smallIcon = iconId
        applicationContext = context.applicationContext
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
        thread {
            val result = prepareNotificationBuilder(message)
            result.getOrNull()?.let { notificationBuilder ->
                getNotificationManager()?.notify(message.id, notificationBuilder.build())
            }
        }
    }
}