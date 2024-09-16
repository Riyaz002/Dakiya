package com.riyaz.dakiya

import android.app.Application

class DakiyaApplication: Application(){
    companion object{
        var instance: DakiyaApplication? = null
        fun getContext() = instance!!
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    override fun onTerminate() {
        instance = null
        super.onTerminate()
    }
}