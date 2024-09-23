package com.riyaz.dakiya.core.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri
import com.riyaz.dakiya.Dakiya

class Initializer: ContentProvider() {
    override fun onCreate(): Boolean {
        Dakiya.init(context!!)
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?) = null
    override fun getType(uri: Uri) = null
    override fun insert(uri: Uri, values: ContentValues?) = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0
}