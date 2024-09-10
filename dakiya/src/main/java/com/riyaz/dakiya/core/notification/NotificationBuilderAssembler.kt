package com.riyaz.dakiya.core.notification


import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.model.Message


fun interface NotificationBuilderAssembler {
    fun assembleBuilder(message: Message): NotificationCompat.Builder
}
