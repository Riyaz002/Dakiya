package com.riyaz.dakiya.core.model

import com.riyaz.dakiya.core.notification.Style

data class Message(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val image: String? = null,
    val style: Style = Style.DEFAULT,
    val smallIcon: Int,
    val channel: String = "default",
    val timer: Timer? = null,
    val themeColor: String? = null
){
    companion object{
        const val ID = "id"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val IMAGE = "image"
        const val STYLE = "style"
        const val SMALL_ICON = "small_icon"
        const val TIME = "time" //uses this format "2024-09-08T12:34:56.OOOZ"
        const val COLOR = "color"
        const val CHANNEL = "channel"
    }
}