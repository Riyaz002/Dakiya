package com.riyaz.dakiya.core.model

import com.riyaz.dakiya.core.Constant.IMAGE
import com.riyaz.dakiya.core.Constant.SUBTITLE
import com.riyaz.dakiya.core.Constant.TITLE
import com.riyaz.dakiya.core.util.getOrNull
import com.riyaz.dakiya.core.util.template.Template
import kotlin.random.Random

data class Notification(
    val id: Int,
    val title: String,
    val subtitle: String? = null,
    val image: String? = null,
    val template: String = Template.DEFAULT.name,
    val channel: String = "default"
){
    constructor(data: Map<String, String>): this(data.getOrNull("id")?.toInt() ?: Random(10000).nextInt(), data.getOrNull(TITLE)!!, data.getOrNull(SUBTITLE), data.getOrNull(IMAGE))
}
