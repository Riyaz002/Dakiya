package com.riyaz.dakiya.core.notification.style

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.riyaz.dakiya.core.model.Message
import com.riyaz.dakiya.core.notification.Style
import com.riyaz.dakiya.core.util.DakiyaException
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageCarouselTest{
    private val message = Message(1, title = "title", subtitle = "subtitle", image = null, style = Style.IMAGE_CAROUSEL, channelID = "default", timer = null)

    @Test
    fun emptyCarouselImages_throwsException(){
        var exception: DakiyaException ?= null
        try {
            message.style.getAssembler().assemble(message)
        } catch (e: Exception){
            exception = e as DakiyaException
        }
        assertTrue(exception is DakiyaException.RequiredFieldNullException)
    }
}