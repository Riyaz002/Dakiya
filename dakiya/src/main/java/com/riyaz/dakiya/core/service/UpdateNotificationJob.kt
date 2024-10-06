package com.riyaz.dakiya.core.service

import android.app.job.JobParameters
import android.app.job.JobService
import com.riyaz.dakiya.core.model.Message.Companion.toDakiyaMessage
import com.riyaz.dakiya.core.getNotificationManager

internal class UpdateNotificationJob: JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        val extras = params?.extras
        val message = extras?.toDakiyaMessage()
        val notification = message?.style?.getAssembler()?.assemble(message)?.build()
        message?.id?.let { getNotificationManager()?.notify(it, notification) }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}