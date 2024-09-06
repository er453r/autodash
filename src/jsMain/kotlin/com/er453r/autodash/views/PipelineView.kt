package com.er453r.autodash.views

import com.er453r.autodash.App.Companion.routing
import com.er453r.autodash.App.Companion.styles
import com.er453r.autodash.events.PipelineClicked
import com.er453r.autodash.utils.async
import com.er453r.autodash.utils.logger
import com.er453r.autodash.utils.on
import io.kvision.html.div
import io.kvision.html.p
import io.kvision.panel.SimplePanel
import io.kvision.state.ObservableValue
import io.kvision.state.bind
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.openapitools.client.apis.PipelineControllerApi
import org.openapitools.client.models.Pipeline
import org.openapitools.client.models.QueueItem
import kotlin.time.Duration.Companion.seconds

class PipelineView : SimplePanel() {
    private var pipelineId:String? = null
    private val pipeline = ObservableValue<Pipeline?>(null)

    private val json = Json {
        prettyPrint = true
    }

    init {
        div().bind(pipeline){
            div {
                +"Pipeline log:"
            }

            div(className = "log") {
                it?.log?.forEach {
                    it.lines.forEach{ line ->
                        p {
                            addCssStyle(styles.codeLine)
                            addCssStyle(styles.codeLineHover)

                            +line.line
                        }
                    }
                }
            }

            div {
                +"Raw log:"
            }

            div {
                addCssStyle(styles.pipelineLog)

                +json.encodeToString(it)
            }

//            document.querySelector(".log p:last-child")?.scrollIntoView(false)
        }

        on<PipelineClicked> {
            logger.info("Clicked pipeline $it")

            routing.navigate("/pipelines/${it.id}")

            pipelineId = it.id
            pipeline.value = null
        }

        update()
    }

    private fun update() {
        async {
            if(pipelineId != null && pipeline.value?.queueItem?.status != QueueItem.Status.DONE) {
                logger.info("Fetching pipeline $pipelineId...")

                pipeline.value = PipelineControllerApi().get(pipelineId!!).body()
            }

            delay(1.seconds)

            update()
        }
    }
}
