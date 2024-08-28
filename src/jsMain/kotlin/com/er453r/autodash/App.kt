package com.er453r.autodash

import io.kvision.*
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.panel.root
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.openapitools.client.apis.UtilsControllerApi

class App : Application() {
    override fun start() {
        root("kvapp") {
            div("Hello world")
            span("Hello world!")
            span("or is it???")
            div("Hello world")
            div("Hello world")
            div("Hello world")
        }

        async {
            println("went great, oh so great!")

            val result = UtilsControllerApi().healthCheck().body().toString()

            println(result)
        }
    }
}

fun async(block: suspend CoroutineScope.() -> Unit){
    CoroutineScope(Dispatchers.Default).launch(block = block)
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
