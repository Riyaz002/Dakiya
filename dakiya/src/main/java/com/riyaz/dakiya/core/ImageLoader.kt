package com.riyaz.dakiya.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.riyaz.dakiya.Dakiya
import com.riyaz.dakiya.core.cache.DiskLruCache
import com.riyaz.dakiya.core.util.tryWithLog
import java.io.File
import java.io.IOException
import java.net.URL
import java.util.concurrent.locks.ReentrantLock


internal object ImageLoader {
    private val lock = ReentrantLock()
    private const val DISK_CACHE_SIZE = 1024*1024*24L
    private var diskCache: DiskLruCache? = null
    private lateinit var cacheDir: File
    @Volatile
    var initialized: Boolean = false

    fun initialize(context: Context){
        synchronized(this){
            lock.lock()
            try {
                if(diskCache==null){
                    cacheDir = DiskLruCache.getDiskCacheDir(context, Dakiya.TAG)
                    diskCache = DiskLruCache.openCache(context, cacheDir, DISK_CACHE_SIZE)
                    initialized = true
                } else initialized = true
            } catch (_: Exception){
            } finally {
                lock.unlock()
            }
        }
    }

    fun getImageBitmap(imageUrl: String?): Bitmap? {
        while(!initialized){
            //wait for initialization to finish
        }
        if (imageUrl == null) return null
        if(diskCache!!.containsKey(imageUrl)){
            return get(imageUrl)
        }
       return tryWithLog("Failed to load image from url: $imageUrl") {
            val url = URL(imageUrl)
            with(url.openConnection().getInputStream()) {
                val bitmap = BitmapFactory.decodeStream(this).also {
                    close()
                }
                add(imageUrl, bitmap)
                bitmap
            }
        }?.getOrNull()
    }

    private fun add(key: String, bitmap: Bitmap){
        // Also add to disk cache
        synchronized(lock) {
            diskCache!!.apply {
                if (!containsKey(key)) {
                    put(key, bitmap)
                }
            }
        }
    }

    private fun get(key: String): Bitmap?{
        return diskCache!![key]
    }

    fun remove(imageUrl: String?){
        if(imageUrl!=null) diskCache?.remove(imageUrl) ?: false
    }
}