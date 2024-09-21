package com.riyaz.dakiya

import java.text.SimpleDateFormat
import java.util.Date

fun convertEpochMillisToFormattedDate(epochMillis: Long): String {
    // Create a DateTimeFormatter with the desired format and set it to UTC
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    // Format the Instant to the desired string
    return formatter.format(Date(epochMillis))
}
