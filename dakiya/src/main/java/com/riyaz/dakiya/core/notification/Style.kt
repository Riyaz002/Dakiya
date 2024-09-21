package com.riyaz.dakiya.core.notification

import android.os.Build
import androidx.annotation.RequiresApi
import com.riyaz.dakiya.core.notification.style.BigTimer
import com.riyaz.dakiya.core.notification.style.Default
import com.riyaz.dakiya.core.notification.style.DefaultImage
import com.riyaz.dakiya.core.notification.style.ImageCarousel
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
    BIG_TIMER(BigTimer::class),
    IMAGE_CAROUSEL(ImageCarousel::class);

    companion object {
        @Throws(DakiyaException::class)
        fun String.getStyle(): Style {
            return when(this) {
                "DEFAULT" -> DEFAULT
                "DEFAULT_IMAGE" -> DEFAULT_IMAGE
                "IMAGE_CAROUSEL" -> IMAGE_CAROUSEL
                "PROGRESS_TIMER" -> if(Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) PROGRESS_TIMER else throw DakiyaException.NoStyleFoundException(this)
                "BIG_TIMER" -> if(Build.VERSION_CODES.N <= Build.VERSION.SDK_INT) BIG_TIMER else throw DakiyaException.NoStyleFoundException(this)
                else -> throw DakiyaException.NoStyleFoundException(this)
            }
        }
    }

    fun getAssembler(): NotificationBuilderAssembler = assemblerClass.createInstance()
}