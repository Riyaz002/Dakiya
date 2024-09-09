package com.riyaz.dakiya.core.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.RemoteViews
import com.riyaz.dakiya.R
import org.json.JSONException
import java.io.IOException
import java.net.URL


fun Map<String, String?>.getOrNull(name: String): String?{
    return try {
        get(name)
    } catch (e: JSONException){
        null
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

fun RemoteViews.performApiLevelConfiguration() {
    if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
        setViewPadding(R.id.ll_root,32, 32, 32,32)
    }
}