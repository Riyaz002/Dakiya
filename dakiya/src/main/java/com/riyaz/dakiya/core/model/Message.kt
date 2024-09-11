package com.riyaz.dakiya.core.model

import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.Constant.IMAGE
import com.riyaz.dakiya.core.Constant.SUBTITLE
import com.riyaz.dakiya.core.Constant.TITLE
import com.riyaz.dakiya.core.util.getOrNull
import com.riyaz.dakiya.core.notification.Style
import com.riyaz.dakiya.core.Constant.TEMPLATE
import com.riyaz.dakiya.core.notification.Style.Companion.getStyle
import kotlin.random.Random

data class Message(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val image: String? = null,
    val style: Style = Style.DEFAULT,
    val smallIcon: Int,
    val channel: String = "default"
)

fun RemoteMessage.toDakiyaMessage(id: Int, channel: String, smallIcon: Int) = Message(
    id = data.getOrNull("id")?.toInt() ?: Random(10000).nextInt(),
    title = data.getOrNull(TITLE)!!,
    subtitle = data.getOrNull(SUBTITLE),
    image = data.getOrNull(IMAGE),
    style = data.getOrNull(TEMPLATE)?.getStyle() ?: Style.DEFAULT,
    smallIcon = smallIcon,
    channel = channel
)