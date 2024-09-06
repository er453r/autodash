package com.er453r.autodash.views

import com.er453r.autodash.App.Companion.styles
import com.er453r.autodash.events.PipelineClicked
import com.er453r.autodash.utils.async
import com.er453r.autodash.utils.dispatch
import com.er453r.autodash.utils.logger
import io.kvision.core.onClick
import io.kvision.form.check.checkBox
import io.kvision.html.div
import io.kvision.html.p
import io.kvision.panel.VPanel
import io.kvision.state.ObservableValue
import io.kvision.state.bindEach
import io.kvision.state.bindTo
import kotlinx.coroutines.delay
import org.openapitools.client.apis.PipelineControllerApi
import org.openapitools.client.models.Pipeline
import kotlin.time.Duration.Companion.seconds

class QueueView : VPanel() {
    private val pipelines = ObservableValue(listOf<Pipeline>())
    private val liveUpdate = ObservableValue(true)

    init {
        addCssStyle(styles.queueView)

        checkBox().bindTo(liveUpdate).apply {
            value = true
            label = "Live update"
        }

        div().bindEach(pipelines) { pipeline ->
            div {
                addCssStyle(styles.pipelineItem)
                addCssStyle(styles.pipelineItemHover)

                p{
                    +"${pipeline.name}  (${pipeline.log.count { it.result == 0 }} / ${pipeline.log.count()})"
                }

                p{
                    +pipeline.queueItem?.status.toString()
                }

                p{
                    +pipeline.createdDate.toString()
                }

                onClick {
                    pipeline.id?.let { dispatch(PipelineClicked(it)) }
                }
            }
        }

        update()
    }

    private fun update() {
        async {
            if (liveUpdate.value) {
                logger.info("Fetching pipelines...")

                pipelines.value = PipelineControllerApi().list().body()
            }

            delay(5.seconds)

            update()
        }
    }
}
