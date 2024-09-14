package com.riyaz.dakiya.core.util

import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.riyaz.dakiya.Dakiya
import org.json.JSONException
import java.io.IOException
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar


fun Map<String, String?>.getOrNull(name: String): String?{
    return try {
        get(name)
    } catch (e: JSONException){
        null
    }
}

fun getImageBitmap(imageUrl: String?): Bitmap? {
    if (imageUrl == null) return null
    try {
        val url = URL(imageUrl)
        with(url.openConnection().getInputStream()) {
            return BitmapFactory.decodeStream(this).also {
                close()
            }
        }
    } catch (e: IOException) {
        return null
    }
}

fun getNotificationManager(): NotificationManager?{
    return if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
        Dakiya.getContext().getSystemService(NotificationManager::class.java)
    } else getSystemService(Dakiya.getContext(), NotificationManager::class.java)
}


fun endsInMillis(date: String): Long {
    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Quoted "Z" to indicate UTC, no timezone offset
    //df.timeZone = TimeZone.getDefault()
    val endDate = df.parse(date)

    val currentTime = Calendar.getInstance().time
    // Calculate the difference in milliseconds
    return endDate.time.minus(currentTime.time)
}

fun lightenColor(color: Int, factor: Float): Int {
    val r = Color.red(color)
    val g = Color.green(color)
    val b = Color.blue(color)
    val a = Color.alpha(color)

    // Apply the lighten factor to each RGB component
    val newR = (r + (255 - r) * factor).toInt().coerceIn(0, 255)
    val newG = (g + (255 - g) * factor).toInt().coerceIn(0, 255)
    val newB = (b + (255 - b) * factor).toInt().coerceIn(0, 255)

    // Return the lighter color as an int
    return Color.argb(a, newR, newG, newB)
}
