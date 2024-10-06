package com.riyaz.dakiya.core.util

import androidx.test.filters.SmallTest
import com.riyaz.dakiya.core.getOrNull
import org.junit.Assert
import org.junit.Test


@SmallTest
class UtilKtTest{

    private val data: Map<String, String> = mapOf(
        "title" to "Big title",
        "subtitle" to "small subtitle",
        "image" to "old image"
    )

    @Test
    fun `Map getOrNull(key) when key not present returns null`(){
        //Querying key which is not present in the map
        val value = data.getOrNull("tilt")
        Assert.assertNull(value)
    }

    @Test
    fun `Map getOrNull(key) when key is present returns value`(){
        //Querying key which is not present in the map
        val value = data.getOrNull("subtitle")
        Assert.assertEquals("small subtitle", value)
    }
}