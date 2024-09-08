package com.riyaz.dakiya.core.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.util.template.NotificationTemplate
import com.riyaz.dakiya.core.util.template.Template
import org.json.JSONException
import java.io.IOException
import java.net.URL


fun Map<String, String?>.getOrNull(name: String): String?{
    return try {
        get(name) as String
    } catch (e: JSONException){
        null
    }
}


fun createChannelIfRequired(notificationManager: NotificationManager, channelId: String){
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
        val channel = notificationManager.notificationChannels.find { it.id == channelId }
        if(channel == null){
            val channel = NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
    }
}

fun getImageBitmap(imageUrl: String?): Bitmap?{
    if(imageUrl == null) return null
    try {
        val url = URL(imageUrl)
        with(url.openConnection().getInputStream()){
            return BitmapFactory.decodeStream(this).also {
                close()
            }
        }
    } catch (e: IOException) {
        return null
    }
}

fun String.getTemplate(): NotificationTemplate {
    return when(this){
        Template.DEFAULT.name -> Template.DEFAULT.template
        else -> throw DakiyaException("No Such Template Found: $this")
    }
}

fun getNotificationManager(): NotificationManager? {
    return if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) Dakiya.getContext().getSystemService<NotificationManager>(NotificationManager::class.java)
    else getSystemService(Dakiya.getContext(), NotificationManager::class.java)
}