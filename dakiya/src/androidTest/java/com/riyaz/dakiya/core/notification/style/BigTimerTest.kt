package com.riyaz.dakiya.core.notification.style

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.riyaz.dakiya.convertEpochMillisToFormattedDate
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.model.Timer
import com.riyaz.dakiya.core.notification.Style
import com.riyaz.dakiya.core.DakiyaException
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith


private const val One_Min_In_Millis = 60*1000

@RunWith(AndroidJUnit4::class)
class BigTimerTest{
    private val currentTimer = System.currentTimeMillis()
    private val futureTime = convertEpochMillisToFormattedDate(currentTimer + One_Min_In_Millis)
    private val message = Message(1, title = "title", subtitle = "subtitle", image = null, style = Style.BIG_TIMER, channelID = "default", timer = Timer(currentTimer, futureTime))

    @Test
    fun api_level_lower_than_N_throw_exception(){
        val willThrowExp = Build.VERSION.SDK_INT < Build.VERSION_CODES.N
        try {
            message.style.getAssembler().assemble(message)
        } catch (e: DakiyaException){
            assertTrue(e is DakiyaException.MinimumApiRequirementException && willThrowExp)
        }
    }

    @Test
    fun buildingWithPastTime_throwsException(){
        val message = Message(1, title = "title", subtitle = "subtitle", image = null, style = Style.BIG_TIMER, channelID = "default", timer = Timer(currentTimer, convertEpochMillisToFormattedDate(currentTimer)))
        var exception: DakiyaException? = null
        try {
            message.style.getAssembler().assemble(message)
        } catch (e: DakiyaException){
            exception = e
        }
        assertTrue(exception is DakiyaException.BuildAfterTimerEndException)
    }

    @Test
    fun buildingWithNullTimer_throwsException(){
        val message = Message(1, title = "title", subtitle = "subtitle", image = null, style = Style.BIG_TIMER, channelID = "default", timer = null)
        var exception: DakiyaException? = null
        try {
            message.style.getAssembler().assemble(message)
        } catch (e: DakiyaException){
            exception = e
        }
        assertTrue(exception is DakiyaException.RequiredFieldNullException)
    }
}