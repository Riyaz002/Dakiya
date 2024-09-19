package com.riyaz.dakiya.core.reciever

import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Message.Companion.toDakiyaMessage
import com.riyaz.dakiya.core.util.getNotificationManager
import kotlin.concurrent.thread
import kotlin.math.abs

internal class NotificationEventReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ACTION_DELETE -> {
                val jobId = intent.extras?.getInt(ID)
                val jobScheduler = getSystemService(context!!, JobScheduler::class.java)
                if (jobId != null) {
                    jobScheduler?.cancel(jobId)
                }
            }
            ACTION_FORWARD,
            ACTION_BACKWARD -> {
                val message = intent.getBundleExtra(DATA)?.toDakiyaMessage() ?: throw IllegalStateException()
                if(message.carousel?.images?.size == null && message.carousel?.currentIndex == null) return
                val indexChange = if(intent.action == ACTION_FORWARD) 1 else -1
                val newIndex = abs(message.carousel.currentIndex + indexChange) % message.carousel.images?.size!!
                message.carousel.currentIndex = newIndex
                val style = message.style.getAssembler()
                thread {
                    val builder = style.assemble(message)
                    getNotificationManager()?.notify(message.id, builder.build())
                }
            }
        }
    }

    companion object{
        const val ACTION_DELETE = "delete"
        const val ACTION_FORWARD = "forward"
        const val ACTION_BACKWARD = "backward"
        const val DATA = "data"
        const val ID = Message.ID
        const val REQUEST_CODE = 12
    }
}