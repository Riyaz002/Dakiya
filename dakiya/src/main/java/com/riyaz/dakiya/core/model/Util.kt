package com.riyaz.dakiya.core.model

import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.model.Message.Companion.COLOR
import com.riyaz.dakiya.core.model.Message.Companion.IMAGE
import com.riyaz.dakiya.core.model.Message.Companion.STYLE
import com.riyaz.dakiya.core.model.Message.Companion.SUBTITLE
import com.riyaz.dakiya.core.model.Message.Companion.TIME
import com.riyaz.dakiya.core.model.Message.Companion.TITLE
import com.riyaz.dakiya.core.notification.Style
import com.riyaz.dakiya.core.notification.Style.Companion.getStyle
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.getOrNull
import java.util.Date
import kotlin.random.Random

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
    themeColor = data.getOrNull(COLOR)
)