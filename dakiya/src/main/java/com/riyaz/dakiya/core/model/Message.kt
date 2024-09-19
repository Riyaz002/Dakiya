package com.riyaz.dakiya.core.model

import android.os.BaseBundle
import android.os.Bundle
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.notification.Style
import com.riyaz.dakiya.core.notification.Style.Companion.getStyle
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.getOrNull
import java.util.Date
import kotlin.random.Random

data class Message(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val image: String? = null,
    val style: Style = Style.DEFAULT,
    val smallIcon: Int,
    val channel: String = "default",
    val timer: Timer? = null,
    val themeColor: String? = null,
    val carousel: Carousel? = null
){
    companion object{
        const val ID = "id"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val IMAGE = "image"
        const val STYLE = "style"
        const val SMALL_ICON = "small_icon"
        const val TIME = "time" //uses this format "2024-09-08T12:34:56.OOOZ"
        const val COLOR = "color"
        const val CURRENT_INDEX = "current_index"
        const val IMAGES = "images"
        const val DOT_COLOR = "dot_color"
        const val START_TIME = "start_time"
        const val CHANNEL = "channel"

        fun BaseBundle.toDakiyaMessage(): Message{
            return Message(
                getInt(ID),
                getString(TITLE)!!,
                getString(SUBTITLE),
                getString(IMAGE),
                getString(STYLE)!!.getStyle(),
                getInt(SMALL_ICON),
                getString(CHANNEL)!!,
                Timer(getLong(START_TIME), getString(TIME)!!),
                getString(COLOR),
                carousel = Carousel(
                    getInt(CURRENT_INDEX),
                    getString(IMAGES)!!.split(","),
                    getString(DOT_COLOR)
                )
            )
        }

        fun Message.toBundle(): BaseBundle {
            val bundle = Bundle()
            bundle.putInt(ID, id)
            bundle.putString(TITLE, title)
            bundle.putString(SUBTITLE, subtitle)
            bundle.putString(IMAGE, image)
            bundle.putString(STYLE, style.name)
            bundle.putInt(SMALL_ICON, smallIcon)
            bundle.putString(CHANNEL, channel)
            bundle.putString(TIME, timer?.endAtString)
            bundle.putLong(START_TIME, System.currentTimeMillis())
            bundle.putString(COLOR, themeColor)
            carousel?.run {
                bundle.putString(IMAGES, images?.joinToString(","))
                currentIndex.let { bundle.putInt(CURRENT_INDEX, it) }
                dotActiveColor.let { bundle.putString(DOT_COLOR, it) }
            }
            return bundle
        }

        @Throws(DakiyaException::class)
        fun RemoteMessage.toDakiyaMessage(channel: String, smallIcon: Int, notificationId: Int? = null) = Message(
            id = notificationId ?: Random(10000).nextInt(),
            title = data.getOrNull(TITLE)!!,
            subtitle = data.getOrNull(SUBTITLE),
            image = data.getOrNull(IMAGE),
            style = data.getOrNull(STYLE)?.getStyle() ?: Style.DEFAULT,
            smallIcon = smallIcon,
            channel = channel,
            timer = data.getOrNull(TIME)?.run { Timer(Date().time, this) },
            themeColor = data.getOrNull(COLOR),
            carousel = if(data.getOrNull(IMAGES)!=null) Carousel(
                data.getOrNull(CURRENT_INDEX)?.toInt() ?: 0,
                data.getOrNull(IMAGES)?.split(","),
                data.getOrNull(DOT_COLOR),
            ) else null
        )
    }
}