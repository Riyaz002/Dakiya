package com.riyaz.dakiya.core.reciever

import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import com.riyaz.dakiya.core.model.Message

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
        }
    }

    companion object{
        const val ACTION_DELETE = "delete"
        const val ID = Message.ID
        const val REQUEST_CODE = 12
    }
}