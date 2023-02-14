package com.stevdza.san.styles

import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.css.percent

val MobileResponsive = ComponentStyle("bs-center-column") {
    base { Modifier.fillMaxWidth(10.percent) }
    Breakpoint.ZERO { Modifier.fillMaxWidth(100.percent) }
}