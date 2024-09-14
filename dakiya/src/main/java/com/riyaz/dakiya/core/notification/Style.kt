package com.riyaz.dakiya.core.notification

import android.os.Build
import androidx.annotation.RequiresApi
import com.riyaz.dakiya.core.notification.style.Default
import com.riyaz.dakiya.core.notification.style.DefaultImage
import com.riyaz.dakiya.core.notification.style.ProgressWithTimer
import com.riyaz.dakiya.core.util.DakiyaException

enum class Style(val builderAssembler: NotificationBuilderAssembler) {
    DEFAULT(Default()),
    DEFAULT_IMAGE(DefaultImage()),
    @RequiresApi(Build.VERSION_CODES.N)
    PROGRESS_TIMER(ProgressWithTimer());

    companion object {
        @Throws(DakiyaException::class)
        fun String.getStyle(): Style {
            return if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                when(this){
                    DEFAULT.name -> DEFAULT
                    DEFAULT_IMAGE.name -> DEFAULT_IMAGE
                    else -> throw DakiyaException("No such style found: $this")
                }
            } else when(this){
                PROGRESS_TIMER.name -> PROGRESS_TIMER
                else -> throw DakiyaException("No such style found: $this")
            }
        }
    }
}