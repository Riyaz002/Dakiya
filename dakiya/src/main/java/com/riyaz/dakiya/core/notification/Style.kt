package com.riyaz.dakiya.core.notification

import android.os.Build
import androidx.annotation.RequiresApi
import com.riyaz.dakiya.core.notification.style.BigTimer
import com.riyaz.dakiya.core.notification.style.Default
import com.riyaz.dakiya.core.notification.style.DefaultImage
import com.riyaz.dakiya.core.notification.style.ProgressWithTimer
import com.riyaz.dakiya.core.util.DakiyaException
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

enum class Style(private val assemblerClass: KClass<out NotificationBuilderAssembler>) {
    DEFAULT(Default::class),
    DEFAULT_IMAGE(DefaultImage::class),
    @RequiresApi(Build.VERSION_CODES.N)
    PROGRESS_TIMER(ProgressWithTimer::class),
    @RequiresApi(Build.VERSION_CODES.N)
    BIG_TIMER(BigTimer::class);

    companion object {
        @Throws(DakiyaException::class)
        fun String.getStyle(): Style {
            return when {
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.N -> when (this) {
                    DEFAULT.name -> DEFAULT
                    DEFAULT_IMAGE.name -> DEFAULT_IMAGE
                    else -> throw DakiyaException("No such style found: $this")
                }
                else -> when (this) {
                    PROGRESS_TIMER.name -> PROGRESS_TIMER
                    BIG_TIMER.name -> BIG_TIMER
                    else -> throw DakiyaException("No such style found: $this")
                }
            }
        }
    }

    fun getAssembler(): NotificationBuilderAssembler = assemblerClass.createInstance()
}