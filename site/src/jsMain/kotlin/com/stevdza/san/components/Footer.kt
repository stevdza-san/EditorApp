package com.stevdza.san.components

import androidx.compose.runtime.Composable
import com.stevdza.san.model.EditorTheme
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.navigation.OpenLinkStrategy
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.*
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

val FooterStyle by ComponentStyle.base {
    Modifier
        .fillMaxWidth()
        .margin(top = 20.px)
        .padding(topBottom = 1.cssRem)
        .fontSize(14.px)
}

val LinkStyle by ComponentStyle {
    anyLink {
        Modifier.color(EditorTheme.RoyalBlue.color)
    }
}

@Composable
fun Footer(modifier: Modifier = Modifier) {
    Row(
        FooterStyle.toModifier().then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Developed by")
        Link(
            modifier = LinkStyle.toModifier().margin(left = 6.px),
            path = "https://www.youtube.com/stevdza_san",
            text = "Stevdza-San",
            openExternalLinksStrategy = OpenLinkStrategy.IN_NEW_TAB
        )
    }
}