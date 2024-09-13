package com.riyaz.dakiya.core.notification

import com.riyaz.dakiya.core.notification.style.Default
import com.riyaz.dakiya.core.notification.style.DefaultImage
import com.riyaz.dakiya.core.notification.style.ProgressWithTimer

enum class Style(val builderAssembler: NotificationBuilderAssembler) {
    DEFAULT(Default()),
    DEFAULT_IMAGE(DefaultImage()),
    PROGRESS_TIMER(ProgressWithTimer());

    companion object {
        fun String.getStyle(): Style {
            return when(this){
                DEFAULT.name -> DEFAULT
                DEFAULT_IMAGE.name -> DEFAULT_IMAGE
                PROGRESS_TIMER.name -> PROGRESS_TIMER
                else -> DEFAULT
            }
        }
    }
}