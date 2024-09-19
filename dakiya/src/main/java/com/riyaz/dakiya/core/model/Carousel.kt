package com.riyaz.dakiya.core.model

data class Carousel(
    var currentIndex: Int = 0,
    val images: List<String>?,
    val dotActiveColor: String?
)