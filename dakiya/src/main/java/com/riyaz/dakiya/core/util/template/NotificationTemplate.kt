package com.riyaz.dakiya.core.util.template

import android.app.Notification


abstract class NotificationTemplate {
    abstract fun build(data: com.riyaz.dakiya.core.model.Notification): Notification
}