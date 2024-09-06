package com.er453r.autodash.styles

import io.kvision.core.*
import io.kvision.utils.em
import io.kvision.utils.px
import io.kvision.utils.vh

class Styles {
    @Suppress("UNUSED")
    val bodyStyle = style("body") {
        background = Background(color = Color.hex(0xfff0f0))
    }

    val mainView = style {
        height = 100.vh
        background = Background(color = Color.hex(0xf0fff0))
    }

    val queueView = style {
        padding = 1.em
    }

    val pipelineItem = style {
        border = Border(1.px,  BorderStyle.SOLID, Color.name(Col.BLACK))
        background = Background(Color.name(Col.WHITE))
        cursor = Cursor.POINTER
    }

    val pipelineItemHover = style(pClass = PClass.HOVER) {
        background = Background(Color.name(Col.SILVER))
    }

    val pipelineLog = style {
        fontFamily = "monospace"
        whiteSpace = WhiteSpace.PRE
        fontSize = 0.8.em
        border = Border(1.px,  BorderStyle.SOLID, Color.name(Col.BLACK))
        background = Background(Color.name(Col.BLACK))
        color = Color.name(Col.WHITE)
    }

    val codeLine = style {
        fontFamily = "monospace"
        whiteSpace = WhiteSpace.PRE
        fontSize = 0.8.em
        border = Border(1.px,  BorderStyle.SOLID, Color.name(Col.BLACK))
        background = Background(Color.name(Col.BLACK))
        color = Color.name(Col.WHITE)
        margin = 0.px
        padding = 0.px
        setStyle("scroll-margin-bottom", "40vh")
    }

    val codeLineHover = style(pClass = PClass.HOVER) {
        background = Background(Color.name(Col.SILVER))
    }
}
