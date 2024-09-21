package com.riyaz.dakiya

import com.riyaz.dakiya.core.model.Message
import org.junit.Test

import org.junit.Assert.*
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder.Result.KotlinClass
import kotlin.reflect.jvm.javaField

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val ins = Message::class.companionObjectInstance
        val outerClass = Message::class.java
        Message::class.companionObject?.declaredMemberProperties?.forEach {
            val myConstantField = outerClass.getDeclaredField(it.name)
            val myConstantValue = myConstantField.get(null)
            println("Name: ${it.name}, value: $myConstantValue")
        }


        // Get the field MY_CONSTANT from the outer class

        // Get the value of the static field (no instance needed, pass null)

    }
}