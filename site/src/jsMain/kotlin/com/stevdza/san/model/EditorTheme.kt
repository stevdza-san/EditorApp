package com.stevdza.san.model

import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.rgb

enum class EditorTheme(val color: CSSColorValue) {
    RoyalBlue(color = rgb(r = 28, g = 181, b = 224)),
    SeaGreen(color = rgb(r = 28, g = 224, b = 153))
}

enum class Theme(val color: CSSColorValue) {
    Red(color = rgb(r = 255, g = 95, b = 86)),
    Yellow(color = rgb(r = 255, g = 189, b = 46)),
    Green(color = rgb(r = 39, g = 201, b = 63))
}