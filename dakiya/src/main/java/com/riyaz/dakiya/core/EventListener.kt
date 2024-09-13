package com.riyaz.dakiya.core

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.model.Event
import com.riyaz.dakiya.core.service.UpdateNotificationJob
import com.riyaz.dakiya.core.service.UpdateNotificationJob.Companion.toPersistentBundle

internal object EventListener {
    fun register(event: Event){
        when(event){
            is Event.ScheduleUpdateNotificationJob -> {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    val jobScheduler = Dakiya.getContext().getSystemService(JobScheduler::class.java)
                    if(jobScheduler.allPendingJobs.find { it.id == event.message.id }!=null){
                        val bundle = event.message.toPersistentBundle()
                        val jobInfo = JobInfo.Builder(event.message.id, ComponentName(Dakiya.getContext(), UpdateNotificationJob::class.java))
                            .setPeriodic(15*60*1000)
                            .setExtras(bundle)
                            .build()
                        jobScheduler.schedule(jobInfo)
                    }
                }
            }
        }
    }
}