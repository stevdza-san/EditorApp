package com.stevdza.san.pages

import androidx.compose.runtime.*
import com.stevdza.san.sections.Footer
import com.stevdza.san.model.EditorTheme
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.styleModifier
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.shapes.Circle
import com.varabyte.kobweb.silk.theme.shapes.clip
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Page
@Composable
fun Editor() {
    var padding by remember { mutableStateOf(50) }
    var fontSize by remember { mutableStateOf(20) }
    var lineHeight by remember { mutableStateOf(20) }
    var theme by remember { mutableStateOf(EditorTheme.RoyalBlue) }
    val breakpoint by rememberBreakpoint()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.percent)
            .gridTemplateRows("1fr auto")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(src = "logo.svg", modifier = Modifier.size(200.px))
            ControlsView(
                defaultPadding = padding,
                defaultFontSize = fontSize,
                defaultLineHeight = lineHeight,
                defaultTheme = theme.name,
                onPaddingInput = { inputValue ->
                    padding = if (inputValue in 10..99)
                        inputValue else 100
                },
                onFontSizeInput = { inputValue ->
                    fontSize = if (inputValue in 10..29)
                        inputValue else 30
                },
                onLineHeightInput = { inputValue ->
                    lineHeight = if (inputValue in 16..39)
                        inputValue else 40
                },
                onThemeSelect = { theme = it },
            )
            Column(
                modifier = Modifier
                    .id("editor")
                    .padding(all = padding.px)
                    .background(theme.color)
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
        Footer(
            modifier = Modifier
                .gridRowStart(2)
                .gridRowEnd(6)
        )
    }
}

@Composable
fun ControlsView(
    defaultPadding: Int,
    defaultFontSize: Int,
    defaultLineHeight: Int,
    defaultTheme: String,
    onPaddingInput: (Int) -> Unit,
    onFontSizeInput: (Int) -> Unit,
    onLineHeightInput: (Int) -> Unit,
    onThemeSelect: (EditorTheme) -> Unit,
) {
    SimpleGrid(
        modifier = Modifier.margin(bottom = 20.px),
        numColumns = numColumns(base = 2, md = 3)
    ) {
        Div(
            attrs = Modifier
                .classNames("form-floating", "my-1", "mx-1")
                .toAttrs()
        ) {
            Input(
                attrs = Modifier
                    .id("font-size")
                    .classNames("form-control")
                    .width(150.px)
                    .toAttrs {
                        name("font-size")
                        placeholder("Font-Size")
                        min("10")
                        max("30")
                        value(defaultFontSize)
                        onInput {
                            val inputValue = it.value?.toInt() ?: 10
                            onFontSizeInput(inputValue)
                        }
                    },
                type = InputType.Number
            )
            Label(forId = "font-size") {
                Text("Font-Size")
            }
        }
        Div(
            attrs = Modifier
                .classNames("form-floating", "my-1", "mx-1")
                .toAttrs()
        ) {
            Input(
                attrs = Modifier
                    .id("line-height")
                    .classNames("form-control")
                    .width(150.px)
                    .toAttrs {
                        name("line-height")
                        placeholder("Line-Height")
                        min("16")
                        max("40")
                        value(defaultLineHeight)
                        onInput {
                            val inputValue = it.value?.toInt() ?: 20
                            onLineHeightInput(inputValue)
                        }
                    },
                type = InputType.Number
            )
            Label(forId = "line-height") {
                Text("Line-Height")
            }
        }
        Div(
            attrs = Modifier
                .classNames("form-floating", "my-1", "mx-1")
                .toAttrs()
        ) {
            Input(
                attrs = Modifier
                    .id("padding")
                    .classNames("form-control")
                    .width(150.px)
                    .toAttrs {
                        name("padding")
                        placeholder("Padding")
                        min("10")
                        max("100")
                        value(defaultPadding)
                        onInput {
                            val inputValue = it.value?.toInt() ?: 10
                            onPaddingInput(inputValue)
                        }
                    },
                type = InputType.Number
            )
            Label(forId = "padding") {
                Text("Padding")
            }
        }
        DropDown(
            selectedTheme = defaultTheme,
            onThemeSelect = onThemeSelect
        )
        Button(attrs = Modifier
            .classNames("btn", "btn-primary", "btn-md", "my-1", "mx-1")
            .onClick {
                js("hljs.highlightAll()") as Unit
            }
            .toAttrs()
        ) {
            Text("Apply Colors")
        }
        Button(attrs = Modifier
            .classNames("btn", "btn-primary", "btn-md", "my-1", "mx-1")
            .onClick { saveImage() }
            .toAttrs()
        ) {
            Text("Export")
        }
    }
}

@Composable
fun DropDown(
    selectedTheme: String,
    onThemeSelect: (EditorTheme) -> Unit
) {
    Div(
        attrs = Modifier
            .classNames("dropdown", "my-1", "mx-1")
            .fillMaxWidth()
            .alignSelf(AlignSelf.Center)
            .toAttrs()
    ) {
        Button(
            attrs = Modifier
                .classNames("btn", "btn-light", "dropdown-toggle", "btn-md")
                .toAttrs {
                    attr("data-bs-toggle", "dropdown")
                }
        ) {
            Text(selectedTheme)
        }
        Ul(attrs = Modifier.classNames("dropdown-menu").toAttrs()) {
            EditorTheme.values().forEach { theme ->
                Li(
                    attrs = Modifier
                        .classNames("dropdown-item")
                        .toAttrs()
                ) {
                    P(
                        attrs = Modifier
                            .onClick { onThemeSelect(theme) }
                            .toAttrs(),
                    ) {
                        Text(theme.name)
                    }
                }
            }
        }
    }
}

@Composable
fun EditorHeader() {
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
                    .backgroundColor("#FF5F56")
                    .size(14.px)
                    .clip(Circle())
                    .margin(right = 10.px),
            )
            Box(
                modifier = Modifier
                    .backgroundColor("#FFBD2E")
                    .size(14.px)
                    .clip(Circle())
                    .margin(right = 10.px)
            )
            Box(
                modifier = Modifier
                    .backgroundColor("#27C93F")
                    .size(14.px)
                    .clip(Circle())
            )
        }
    }
}

@Composable
fun EditorBody(
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
        .overflow(overflows = arrayOf(Overflow.Hidden))
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

private fun saveImage() {
    js(
        "var node = document.getElementById('editor');\n" +
                "\n" +
                "var options = {\n" +
                "    quality: 0.99,\n" +
                "    width: node.clientWidth*4,\n" +
                "    height: node.clientHeight*4,\n" +
                "    style: {\n" +
                "    'transform': 'scale(4)',\n" +
                "    'transform-origin': 'top left',\n" +
                "}\n" +
                "};\n" +
                "\n" +
                "domtoimage.toBlob(node, options).then(function(blob) {\n" +
                "    window.saveAs(blob, 'pretty-ko.png');\n" +
                "});"
    )
}