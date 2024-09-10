package com.riyaz.dakiya.core.notification

import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.notification.style.Default
import com.riyaz.dakiya.core.notification.style.DefaultImage

internal enum class Style(val style: NotificationStyle) {
    DEFAULT(Default(Dakiya.getContext())),
    DEFAULT_IMAGE(DefaultImage(Dakiya.getContext()));

    companion object {
        fun String.getStyle(): NotificationStyle {
            return when(this){
                DEFAULT.name -> DEFAULT.style
                DEFAULT_IMAGE.name -> DEFAULT_IMAGE.style
                else -> DEFAULT.style
            }
        }
    }
}