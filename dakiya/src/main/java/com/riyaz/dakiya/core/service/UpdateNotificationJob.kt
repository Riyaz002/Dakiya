package com.riyaz.dakiya.core.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.PersistableBundle
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Message.Companion.COLOR
import com.riyaz.dakiya.core.model.Timer
import com.riyaz.dakiya.core.notification.Style.Companion.getStyle
import com.riyaz.dakiya.core.util.getNotificationManager

class UpdateNotificationJob: JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        val extras = params!!.extras
        val message = extras.toDakiyaMessage()
        val notification = message.style.builderAssembler.assemble(message).build()
        getNotificationManager()?.notify(message.id, notification)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }


    companion object{
        private const val START_TIME = "start-time"


        fun PersistableBundle.toDakiyaMessage(): Message{
            return Message(
                getInt(Message.ID),
                getString(Message.TITLE)!!,
                getString(Message.SUBTITLE),
                getString(Message.IMAGE),
                getString(Message.STYLE)!!.getStyle(),
                getInt(Message.SMALL_ICON),
                getString(Message.CHANNEL)!!,
                Timer(getLong(START_TIME), getString(Message.TIME)!!),
                getString(COLOR),
            )
        }


        fun Message.toPersistentBundle(): PersistableBundle{
            val bundle = PersistableBundle()
            bundle.putInt(Message.ID, id)
            bundle.putString(Message.TITLE, title)
            bundle.putString(Message.SUBTITLE, subtitle)
            bundle.putString(Message.IMAGE, image)
            bundle.putString(Message.STYLE, style.name)
            bundle.putInt(Message.SMALL_ICON, smallIcon)
            bundle.putString(Message.CHANNEL, channel)
            bundle.putString(Message.TIME, timer?.endAtString)
            bundle.putLong(START_TIME, System.currentTimeMillis())
            bundle.putString(COLOR, themeColor)
            return bundle
        }
    }
}