package com.riyaz.dakiya.core.notification


import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.util.DakiyaException


fun interface NotificationBuilderAssembler {
    @Throws(DakiyaException::class)
    fun assemble(message: Message): NotificationCompat.Builder
}
