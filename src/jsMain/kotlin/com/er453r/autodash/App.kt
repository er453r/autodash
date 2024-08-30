package com.er453r.autodash

import com.er453r.autodash.utils.init
import com.er453r.autodash.utils.logger
import io.kvision.*
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.panel.root
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.openapitools.client.apis.PipelineControllerApi
import org.openapitools.client.apis.UtilsControllerApi
import org.openapitools.client.models.Pipeline

class App : Application() {
    override fun start() {
        root("kvapp") {
            div("Hello world")
            span("Hello world!")
            span("or is it???")
            div("Hello world")
        }

        async {
            println("went great, oh so great!")

            val result = UtilsControllerApi().healthCheck().body().toString()

            println(result)
        }

        logger.init()

        logger.info("This is an info test")
        logger.warn("This is a warning test")
        logger.error("This is an error test")

        update()
    }

    private fun update() {
        async {
            val list: List<Pipeline> = PipelineControllerApi().list().body()
            println(list)

            delay(1000)

            update()
        }
    }
}

fun async(block: suspend CoroutineScope.() -> Unit) {
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
