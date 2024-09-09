package com.riyaz.dakiya.core.util.template

import com.riyaz.dakiya.Dakiya

enum class Template(val template: NotificationTemplate) {
    DEFAULT(Default(Dakiya.getContext())),
    DEFAULT_IMAGE(DefaultImage(Dakiya.getContext()))
}