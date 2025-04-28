package com.riyaz.dakiya.core.util

import android.util.Log
import com.riyaz.dakiya.Dakiya

fun log(message: String){
    Log.d(Dakiya.TAG, message)
}

inline fun <T> tryWithLog(onFailMessage: String? = null, op: () -> T?): Result<T?>?{
    return try {
        Result.success(op())
    } catch (e: Exception){
        log(message = e.message.toString())
        Result.failure(e)
    }
}