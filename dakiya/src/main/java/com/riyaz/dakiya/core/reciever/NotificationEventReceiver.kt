package com.riyaz.dakiya.core.reciever

import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.ImageLoader
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Message.Companion.toDakiyaMessage
import com.riyaz.dakiya.core.notification.Style
import com.riyaz.dakiya.core.getNotificationManager
import kotlin.concurrent.thread
import kotlin.math.abs

internal class NotificationEventReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ACTION_CLICK -> {
                val link = intent.extras?.getString(LINK)?:return
                Dakiya.onClick!!.invoke(link)
            }
            ACTION_DELETE -> {
                val message = intent.getBundleExtra(DATA)?.toDakiyaMessage() ?: throw IllegalStateException()

                //removing job related to updating the notification
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && message.style == Style.PROGRESS_TIMER){
                    val jobId = intent.extras?.getInt(Message.ID)
                    val jobScheduler = getSystemService(context!!, JobScheduler::class.java)
                    if (jobId != null) {
                        jobScheduler?.cancel(jobId)
                    }
                }
                //removing cached image of the notification
                message.carousel?.images?.forEach { ImageLoader.remove(it) }
                ImageLoader.remove(message.image)
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
        const val ACTION_CLICK = "click"
        const val ACTION_DELETE = "delete"
        const val ACTION_FORWARD = "forward"
        const val ACTION_BACKWARD = "backward"
        const val LINK = "link"
        const val DATA = "data"
        const val REQUEST_CODE = 12
    }
}