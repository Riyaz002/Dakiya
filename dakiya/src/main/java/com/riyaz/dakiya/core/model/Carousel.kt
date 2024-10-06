package com.riyaz.dakiya.core.model

import android.os.BaseBundle
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.getOrNull

data class Carousel(
    var currentIndex: Int = 0,
    val images: List<String>?,
    val dotActiveColor: String?
){
    companion object{
        private const val CURRENT_INDEX = "current_index"
        private const val IMAGES = "images"
        private const val DOT_COLOR = "dot_color"

        fun BaseBundle.putCarousel(carousel: Carousel?){
            if(carousel == null) return
            putInt(CURRENT_INDEX, carousel.currentIndex)
            putString(IMAGES, carousel.images?.joinToString(","))
            putString(DOT_COLOR, carousel.dotActiveColor)
        }

        fun BaseBundle.getCarousel(): Carousel?{
            return if(containsKey(IMAGES)) Carousel(
                getInt(CURRENT_INDEX),
                getString(IMAGES)?.split(","),
                getString(DOT_COLOR)
            ) else null
        }

        fun RemoteMessage.getCarousel(): Carousel?{
            return if(data.containsKey(IMAGES)) Carousel(
                data.getOrNull(CURRENT_INDEX)?.toInt()?:0,
                data.getOrNull(IMAGES)?.split(","),
                data.getOrNull(DOT_COLOR)
            ) else null
        }
    }
}