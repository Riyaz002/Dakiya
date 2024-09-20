package com.riyaz.dakiya.core.model

import android.os.BaseBundle
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.util.getOrNull

data class Timer(
    val startedAt: Long,
    val endAtString: String
) {
    companion object {
        private const val TIME = "time" //uses this format "2024-09-08T12:34:56.OOOZ"
        private const val START_TIME = "start_time"

        infix fun BaseBundle.add(timer: Timer?){
            if (timer == null) return
            putString(TIME, timer.endAtString)
            putLong(START_TIME, timer.startedAt)
        }

        fun BaseBundle.getTimer(): Timer? {
            return if (containsKey(TIME)) Timer(
                getLong(START_TIME),
                getString(TIME)!!
            ) else null
        }

        fun RemoteMessage.getTimer(): Timer? {
            return if (data.containsKey(TIME)) Timer(
                data[START_TIME]?.toLong() ?: System.currentTimeMillis(),
                data.getOrNull(TIME)!!
            ) else null
        }
    }
}