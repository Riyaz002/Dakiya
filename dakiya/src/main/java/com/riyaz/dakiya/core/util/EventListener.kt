package com.riyaz.dakiya.core.util

import android.app.NotificationManager
import android.os.Build

object EventListener {
    fun emit(event: Event){
        when(event){
            is Event.Build -> {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    val notificationManager = event.context.getSystemService(NotificationManager::class.java)
                    createChannelIfRequired(notificationManager, event.channelId)
                }
            }
        }
    }
}