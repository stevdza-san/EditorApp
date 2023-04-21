package com.stevdza.san.components

import androidx.compose.runtime.*
import com.stevdza.san.model.EditorTheme
import com.stevdza.san.model.Theme
import com.stevdza.san.util.Res
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.shapes.Circle
import com.varabyte.kobweb.silk.theme.shapes.clip
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text

@Composable
fun Editor() {
    var padding by remember { mutableStateOf(50) }
    var fontSize by remember { mutableStateOf(20) }
    var lineHeight by remember { mutableStateOf(20) }
    var editorTheme by remember { mutableStateOf(EditorTheme.RoyalBlue) }
    val breakpoint by rememberBreakpoint()

    ControlsView(
        defaultPadding = padding,
        defaultFontSize = fontSize,
        defaultLineHeight = lineHeight,
        defaultTheme = editorTheme.name,
        onPaddingChanged = { value ->
            padding = if (value in 10..99)
                value else 100
        },
        onFontSizeChanged = { value ->
            fontSize = if (value in 14..29)
                value else 30
        },
        onLineHeightChanged = { value ->
            lineHeight = if (value in 16..39)
                value else 40
        },
        onThemeSelect = { selectedTheme ->
            editorTheme = EditorTheme.values().find { themes ->
                themes.color == selectedTheme
            }!!
        },
    )
    Column(
        modifier = Modifier
            .id(Res.Id.editor)
            .padding(all = padding.px)
            .backgroundColor(editorTheme.color)
    ) {
        EditorHeader()
        EditorBody(
            fontSize = fontSize,
            lineHeight = lineHeight,
            padding = padding,
            breakpoint = breakpoint
        )
    }
}

@Composable
private fun EditorHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.px)
            .backgroundColor(Colors.White)
            .borderRadius(
                topLeft = 6.px,
                topRight = 6.px,
                bottomLeft = 0.px,
                bottomRight = 0.px
            )
    ) {
        Row {
            Box(
                modifier = Modifier
                    .backgroundColor(Theme.Red.color)
                    .size(14.px)
                    .clip(Circle())
                    .margin(right = 10.px),
            )
            Box(
                modifier = Modifier
                    .backgroundColor(Theme.Yellow.color)
                    .size(14.px)
                    .clip(Circle())
                    .margin(right = 10.px)
            )
            console.log()
            Box(
                modifier = Modifier
                    .backgroundColor(Theme.Green.color)
                    .size(14.px)
                    .clip(Circle())
            )
        }
    }
}

@Composable
private fun EditorBody(
    fontSize: Int,
    lineHeight: Int,
    padding: Int,
    breakpoint: Breakpoint
) {
    Pre(attrs = Modifier
        .width(if (breakpoint <= Breakpoint.MD) 100.vw - padding.px * 2 else 740.px)
        .height(350.px)
        .minWidth(112.px)
        .padding(all = 20.px)
        .margin(bottom = 0.px)
        .backgroundColor(Colors.White)
        .borderRadius(
            bottomLeft = 6.px,
            bottomRight = 6.px,
            topLeft = 0.px,
            topRight = 0.px
        )
        .border(width = 0.px)
        .outline(style = LineStyle.None)
        .overflow(overflow = Overflow.Hidden)
        .styleModifier {
            property("resize", "both")
        }
        .toAttrs()
    ) {
        Code(attrs = Modifier
            .contentEditable(true)
            .spellCheck(false)
            .classNames("language-kotlin")
            .outline(style = LineStyle.None)
            .styleModifier {
                fontFamily("Roboto", "sans-serif")
                fontSize(fontSize.px)
                lineHeight(lineHeight.px)
            }
            .toAttrs {
                onPaste {
                    it.preventDefault()
                    val text = it.clipboardData?.getData("Text")
                    if (text != null) {
                        document.execCommand(
                            "insertHtml",
                            false,
                            "<code class=\"language-kotlin\" style=\"width: 100%; height: 100%; font-family: Roboto, sans-serif; font-size: ${fontSize.px}; line-height: ${lineHeight.px}; overflow: hidden;\">" + text + "</code>"
                        )
                    }
                }
            }
        ) {
            Text(
                """
                private val credits = "Kobweb Framework"
            """.trimIndent()
            )
        }
    }
}