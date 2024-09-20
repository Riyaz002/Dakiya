package com.riyaz.dakiya.core.model

import android.os.BaseBundle
import android.os.Bundle
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.model.Carousel.Companion.add
import com.riyaz.dakiya.core.model.Timer.Companion.add
import com.riyaz.dakiya.core.model.Carousel.Companion.getCarousel
import com.riyaz.dakiya.core.model.Timer.Companion.getTimer
import com.riyaz.dakiya.core.notification.Style
import com.riyaz.dakiya.core.notification.Style.Companion.getStyle
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.getOrNull
import kotlin.random.Random

data class Message(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val image: String? = null,
    val style: Style = Style.DEFAULT,
    val channel: String = "default",
    val timer: Timer? = null,
    val themeColor: String? = null,
    val carousel: Carousel? = null,
    val cta: String? = null,
    val button1: String? = null,
    val button2: String? = null,
    val button3: String? = null,
){
    companion object{
        const val ID = "id"
        private const val TITLE = "title"
        private const val SUBTITLE = "subtitle"
        private const val IMAGE = "image"
        private const val STYLE = "style"
        private const val COLOR = "color"
        private const val CTA = "cta"
        private const val BUTTON_1 = "button1"
        private const val BUTTON_2 = "button2"
        private const val BUTTON_3 = "button3"
        private const val CHANNEL = "channel"

        fun BaseBundle.toDakiyaMessage(): Message{
            return Message(
                id = getInt(ID),
                title = getString(TITLE)!!,
                subtitle = getString(SUBTITLE),
                image = getString(IMAGE),
                style = getString(STYLE)!!.getStyle(),
                channel = getString(CHANNEL)!!,
                timer = getTimer(),
                themeColor = getString(COLOR),
                carousel = this.getCarousel(),
                cta = getString(CTA),
                button1 = getString(BUTTON_1),
                button2 = getString(BUTTON_2),
                button3 = getString(BUTTON_3)
            )
        }

        fun Message.toBundle(): BaseBundle {
            val bundle = Bundle()
            bundle.putInt(ID, id)
            bundle.putString(TITLE, title)
            bundle.putString(SUBTITLE, subtitle)
            bundle.putString(IMAGE, image)
            bundle.putString(STYLE, style.name)
            bundle.putString(CHANNEL, channel)
            bundle.putString(COLOR, themeColor)
            bundle.putString(CTA, cta)
            bundle.putString(BUTTON_1, button1)
            bundle.putString(BUTTON_2, button2)
            bundle.putString(BUTTON_3, button3)
            bundle add timer
            bundle add carousel
            return bundle
        }

        @Throws(DakiyaException::class)
        fun RemoteMessage.toDakiyaMessage(notificationId: Int? = null) = Message(
            id = notificationId ?: Random(10000).nextInt(),
            title = data.getOrNull(TITLE)!!,
            subtitle = data.getOrNull(SUBTITLE),
            image = data.getOrNull(IMAGE),
            style = data.getOrNull(STYLE)?.getStyle() ?: Style.DEFAULT,
            channel = data.getOrNull(CHANNEL)?:"default",
            timer = getTimer(),
            themeColor = data.getOrNull(COLOR),
            carousel = getCarousel(),
            cta = data.getOrNull(CTA),
            button1 = data.getOrNull(BUTTON_1),
            button2 = data.getOrNull(BUTTON_2),
            button3 = data.getOrNull(BUTTON_3),
        )
    }
}