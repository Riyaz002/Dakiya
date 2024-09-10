package com.riyaz.dakiya.core.notification

import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.notification.style.Default
import com.riyaz.dakiya.core.notification.style.DefaultImage

enum class Style(val builderAssembler: NotificationBuilderAssembler) {
    DEFAULT(Default(Dakiya.getContext())),
    DEFAULT_IMAGE(DefaultImage(Dakiya.getContext()));

    companion object {
        fun String.getStyle(): Style {
            return when(this){
                DEFAULT.name -> DEFAULT
                DEFAULT_IMAGE.name -> DEFAULT_IMAGE
                else -> DEFAULT
            }
        }
    }
}