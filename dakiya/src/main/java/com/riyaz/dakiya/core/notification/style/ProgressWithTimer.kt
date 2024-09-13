package com.riyaz.dakiya.core.notification.style

import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver
import com.riyaz.dakiya.core.reciever.NotificationEventReceiver.Companion.ACTION_DELETE
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.R
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.NotificationBuilderAssembler
import com.riyaz.dakiya.core.service.UpdateNotificationJob
import com.riyaz.dakiya.core.util.DakiyaException
import com.riyaz.dakiya.core.util.endsInMillis
import com.riyaz.dakiya.core.util.getImageBitmap
import com.riyaz.dakiya.core.util.getNotificationManager
import com.riyaz.dakiya.core.util.lightenColor
import com.riyaz.dakiya.core.util.performApiLevelConfiguration
import java.util.Date



internal class ProgressWithTimer: NotificationBuilderAssembler {

    override fun assemble(message: Message): NotificationCompat.Builder {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) throw DakiyaException("Minimum required API level for this style is 24")

        val builder = NotificationCompat.Builder(Dakiya.getContext(), message.channel)
            .setContentTitle(message.title)
            .setContentText(message.subtitle)
            .setSmallIcon(message.smallIcon)
            .setWhen(message.timer!!.startedAt)
            .setAutoCancel(true)

        val notificationDeleteIntent = Intent(Dakiya.getContext(), NotificationEventReceiver::class.java).also {
            it.action = ACTION_DELETE
            it.putExtra(NotificationEventReceiver.ID, message.id)
        }
        val deleteJobIntent = PendingIntent.getBroadcast(
            Dakiya.getContext(),
            NotificationEventReceiver.REQUEST_CODE,
            notificationDeleteIntent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.setDeleteIntent(deleteJobIntent)

        if(getNotificationManager()?.activeNotifications?.find { it.id == message.id }!=null){
            builder.setSilent(true)
        }

        val endInMillis = endsInMillis(message.timer.endAtString)
        val startEpoch = message.timer.startedAt
        val currentEpoch = Date().time.plus(4L*60L*60L*1000L)
        val endEpoch = currentEpoch + endInMillis

        var currentPerc = ((currentEpoch - startEpoch).toDouble() / (endEpoch - startEpoch).toDouble()) * 100L

        if(currentPerc.toInt()==0){
            currentPerc = 3.0
        }


        val themeColor = Color.parseColor(message.themeColor)

        val collapsedView = RemoteViews(Dakiya.getContext().packageName, R.layout.progress_timer_collapsed)
        collapsedView.performApiLevelConfiguration()
        collapsedView.setTextViewText(R.id.notification_title, message.title)
        collapsedView.setTextViewText(R.id.notification_subtitle, message.subtitle)
        collapsedView.setChronometerCountDown(R.id.notification_timer, true)
        collapsedView.setChronometer(R.id.notification_timer, (SystemClock.elapsedRealtime()+endInMillis), null, true)
        collapsedView.setInt(R.id.notification_timer, "setTextColor", themeColor)

        val expandedView = RemoteViews(Dakiya.getContext().packageName, R.layout.progress_timer_expanded)
        expandedView.performApiLevelConfiguration()
        expandedView.setTextViewText(R.id.notification_title, message.title)
        expandedView.setTextViewText(R.id.notification_subtitle, message.subtitle)
        expandedView.setChronometerCountDown(R.id.notification_timer, true)
        expandedView.setChronometer(R.id.notification_timer, (SystemClock.elapsedRealtime()+endInMillis), null, true)
        expandedView.setProgressBar(R.id.notification_progress, 100, currentPerc.toInt(), false)
        expandedView.setInt(R.id.notification_timer, "setTextColor", themeColor)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S) {
            val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
            val colors = intArrayOf(themeColor, lightenColor(themeColor, 0.2f))
            val colorStateList = ColorStateList(states, colors)
            expandedView.setColorStateList(R.id.notification_progress, "setProgressTintList", colorStateList)
            expandedView.setColorStateList(R.id.notification_progress, "setProgressBackgroundTintList", colorStateList)
        }



        message.image?.let {
            val image = getImageBitmap(it)
            expandedView.setViewVisibility(R.id.notification_image, View.VISIBLE)
            expandedView.setImageViewBitmap(R.id.notification_image, image)
            collapsedView.setViewVisibility(R.id.notification_image, View.VISIBLE)
            collapsedView.setImageViewBitmap(R.id.notification_image, image)
        }

        builder.setCustomContentView(collapsedView)
        builder.setCustomBigContentView(expandedView)
        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle())

        //Update notification progress using a job
        val jobInfo = JobInfo.Builder(message.id, ComponentName(Dakiya.getContext(), UpdateNotificationJob::class.java))
            .setPeriodic(15*60*1000).build()

        val jobScheduler = Dakiya.getContext().getSystemService(JobScheduler::class.java)
        jobScheduler.schedule(jobInfo)
        return builder
    }

}
