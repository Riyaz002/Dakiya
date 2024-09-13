package com.riyaz.dakiya.core.model

sealed interface Event {
    data class ScheduleUpdateNotificationJob(val message: Message): Event
}