package com.stevdza.san.pages

import androidx.compose.runtime.Composable
import com.stevdza.san.components.Editor
import com.stevdza.san.sections.Footer
import com.stevdza.san.util.Res
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun HomePage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.percent)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(src = Res.Image.logo, modifier = Modifier.size(200.px))
            Editor()
        }
        Footer(
            modifier = Modifier
                .gridRowStart(2)
                .gridRowEnd(6)
        )
    }
}