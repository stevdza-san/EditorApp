package com.stevdza.san.pages

import androidx.compose.runtime.*
import com.stevdza.san.model.EditorTheme
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.dom.ref
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
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.theme.shapes.Circle
import com.varabyte.kobweb.silk.theme.shapes.clip
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.Element

@Page(routeOverride = "/Editor")
@Composable
fun Editor() {
    var padding by remember { mutableStateOf(50) }
    var fontSize by remember { mutableStateOf(20) }
    var theme by remember { mutableStateOf(EditorTheme.RoyalBlue) }
    var code: Element? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ControlsView(
            defaultPadding = padding,
            defaultFontSize = fontSize,
            defaultTheme = theme.name,
            onPaddingInput = { inputValue ->
                padding = if (inputValue in 10..99)
                    inputValue else 100
            },
            onFontSizeInput = { inputValue ->
                fontSize = if (inputValue in 10..29)
                    inputValue else 30
            },
            onThemeSelect = { theme = it }
        )
        Column(
            modifier = Modifier
                .id("editor")
                .padding(all = padding.px)
                .background(theme.color),
            ref = ref {
                code = document.getElementById("code")
            }
        ) {
            EditorHeader()
            EditorBody(fontSize = fontSize)
        }
    }
}

@Composable
fun ControlsView(
    defaultPadding: Int,
    defaultFontSize: Int,
    defaultTheme: String,
    onPaddingInput: (Int) -> Unit,
    onFontSizeInput: (Int) -> Unit,
    onThemeSelect: (EditorTheme) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Div(
            attrs = Modifier
                .classNames("form-floating", "mb-3", "mt-3")
                .margin(right = 10.px)
                .toAttrs()
        ) {
            Input(
                attrs = Modifier
                    .classNames("form-control")
                    .width(150.px)
                    .id("font-size")
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
                .classNames("form-floating", "mb-3", "mt-3")
                .margin(right = 10.px)
                .toAttrs()
        ) {
            Input(
                attrs = Modifier
                    .classNames("form-control")
                    .width(150.px)
                    .id("padding")
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
            .margin(right = 10.px)
            .classNames("btn", "btn-primary", "btn-lg")
            .onClick {
                js("hljs.highlightAll()") as Unit
            }
            .toAttrs()
        ) {
            Text("Apply Colors")
        }
        Button(attrs = Modifier
            .classNames("btn", "btn-primary", "btn-lg")
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
            .classNames("dropdown")
            .margin(right = 10.px)
            .toAttrs()
    ) {
        Button(
            attrs = Modifier
                .classNames("btn", "btn-light", "dropdown-toggle", "btn-lg")
                .toAttrs {
                    attr("data-bs-toggle", "dropdown")
                }
        ) {
            Text(selectedTheme)
        }
        Ul(attrs = Modifier.classNames("dropdown-menu").toAttrs()) {
            EditorTheme.values().forEach { theme ->
                Li {
                    P(
                        attrs = Modifier
                            .classNames("dropdown-item")
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
                    .margin(right = 10.px)
            )
        }
    }
}

@Composable
fun EditorBody(fontSize: Int) {
    KotlinCode(fontSize = fontSize)
}

@Composable
fun KotlinCode(
    fontSize: Int,
    modifier: Modifier = Modifier
) {
    var pastingEnabled by remember { mutableStateOf(false) }
    Pre(attrs = Modifier
        .minWidth(112.px)
        .padding(all = 20.px)
        .margin(bottom = 0.px)
        .backgroundColor(Colors.White)
        .contentEditable(true)
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
        .toAttrs {
//            onKeyUp {
//                if (it.ctrlKey && it.code == "KeyV" && !it.shiftKey) {
//                    js("alert(\"Use CTRL+SHIFT+V instead.\");")
//                    pastingEnabled = false
//                } else if (it.ctrlKey && it.shiftKey && it.code == "KeyV") {
//                    pastingEnabled = true
//                }
//            }
            onPaste {
                it.preventDefault()
                val text = it.clipboardData?.getData("Text")
                document.execCommand("insertHtml", false, "<code id=\"code\" class=\"language-kotlin\" style=\"width: 100%; height: 100%; font-family: Roboto, sans-serif; font-size: ${fontSize.px}; overflow: hidden;\">" + text!! + "</code>")
//                val data = it.clipboardData?.getData("Text")
//                console.log(data)
//                if (!pastingEnabled) {
//                    it.preventDefault()
//                    document.execCommand("insertHtml", false, "")
//                }
            }
        }
    ) {
        Code(attrs = modifier
            .id("code")
            .fillMaxSize()
            .classNames("language-kotlin")
            .styleModifier {
                fontFamily("Roboto", "sans-serif")
                fontSize(fontSize.px)
                overflow("hidden")
            }
            .toAttrs()
        ) {
            Text("""
                @Page
                @Composable
                fun HomePage() {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(value = "About")
                        Link(path = "/Editor", text = "Editor")
                    }
                }
            """.trimIndent())
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