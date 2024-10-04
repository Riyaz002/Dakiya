package com.riyaz.dakiya.core.model

import android.os.Bundle
import com.google.firebase.messaging.RemoteMessage
import com.riyaz.dakiya.core.model.Message.Companion.ID
import com.riyaz.dakiya.core.model.Message.Companion.toBundle
import com.riyaz.dakiya.core.model.Message.Companion.toDakiyaMessage
import com.riyaz.dakiya.core.model.Message.Companion.toPersistableBundle
import com.riyaz.dakiya.core.model.Timer.Companion.START_TIME
import com.riyaz.dakiya.core.notification.Style
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MessageModelTest{

    private val message = Message(1, "title", "Subtitle", "Image", Style.DEFAULT, "default", Timer(111, "2024-09-08T12:34:56.000Z"), "#FF0000", Carousel(0, images = listOf("1", "2", "3"), "#441144"), "Cta link", button1 = "b1", button2 = "b2", button3 = "b3")

    @Test
    fun `Message to Bundle conversion`(){
        val bundle = message.toBundle()
        val restoredMessage = bundle.toDakiyaMessage()

        assertEquals(restoredMessage, message)
    }

    @Test
    fun `Message to persistence bundle conversion`(){
        val bundle = message.toPersistableBundle()
        val restoredMessage = bundle.toDakiyaMessage()

        assertEquals(restoredMessage, message)
    }

    @Test
    fun `RemoteMessage to Message conversion`(){
        val bundle = message.toBundle()
        val remoteMessage = RemoteMessage(bundle as Bundle)
        val restoredMessage = remoteMessage.toDakiyaMessage()
        val restoredBundle = restoredMessage.toBundle()

        for(key in restoredBundle.keySet()){
            /**
             * Excluding [ID] and [START_TIME] as they are or type [Int]. Since, [RemoteMessage] only read values from [Bundle] that are of type [String]
             */
            if(key!=ID && key!= START_TIME){
                assert(restoredBundle.get(key) == bundle.get(key))
            }
        }
    }
}