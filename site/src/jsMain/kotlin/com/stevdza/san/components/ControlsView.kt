package com.stevdza.san.components

import androidx.compose.runtime.Composable
import com.stevdza.san.model.EditorTheme
import com.stevdza.san.util.Res
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun ControlsView(
    defaultPadding: Int,
    defaultFontSize: Int,
    defaultLineHeight: Int,
    defaultTheme: String,
    onPaddingChanged: (Int) -> Unit,
    onFontSizeChanged: (Int) -> Unit,
    onLineHeightChanged: (Int) -> Unit,
    onThemeSelect: (CSSColorValue) -> Unit,
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
                    .id(Res.Id.fontSize)
                    .classNames("form-control")
                    .width(150.px)
                    .toAttrs {
                        name("font-size")
                        placeholder("Font-Size")
                        min("14")
                        max("30")
                        value(defaultFontSize)
                        onInput {
                            val inputValue = it.value?.toInt() ?: 14
                            onFontSizeChanged(inputValue)
                        }
                    },
                type = InputType.Number
            )
            Label(forId = Res.Id.fontSize) {
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
                    .id(Res.Id.lineHeight)
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
                            onLineHeightChanged(inputValue)
                        }
                    },
                type = InputType.Number
            )
            Label(forId = Res.Id.lineHeight) {
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
                    .id(Res.Id.padding)
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
                            onPaddingChanged(inputValue)
                        }
                    },
                type = InputType.Number
            )
            Label(forId = Res.Id.padding) {
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
private fun DropDown(
    selectedTheme: String,
    onThemeSelect: (CSSColorValue) -> Unit
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
        Ul(attrs = Modifier
            .classNames("dropdown-menu")
            .cursor(Cursor.Pointer)
            .toAttrs()) {
            EditorTheme.values().forEach { theme ->
                Li(
                    attrs = Modifier
                        .classNames("dropdown-item")
                        .toAttrs()
                ) {
                    P(
                        attrs = Modifier
                            .onClick { onThemeSelect(theme.color) }
                            .toAttrs(),
                    ) {
                        Text(theme.name)
                    }
                }
            }
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