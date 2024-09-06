package com.er453r.autodash.styles

import io.kvision.core.Background
import io.kvision.core.Color
import io.kvision.core.style
import io.kvision.utils.vh

class Styles {
    val bodyStyle = style("body") {
        background = Background(color = Color.hex(0xfff0f0))
    }

    val mainView = style {
        height = 100.vh
        background = Background(color = Color.hex(0xf0fff0))
    }
}
