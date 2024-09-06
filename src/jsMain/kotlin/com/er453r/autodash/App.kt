package com.er453r.autodash

import com.er453r.autodash.styles.Styles
import com.er453r.autodash.views.MainView
import io.kvision.*
import io.kvision.panel.root
import io.kvision.routing.Routing

class App : Application() {
    companion object {
        val routing = Routing.init()
        val styles = Styles()
    }

    override fun start() {
        root("kvapp") {
            add(MainView())
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}
