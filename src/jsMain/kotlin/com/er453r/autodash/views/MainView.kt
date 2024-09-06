package com.er453r.autodash.views

import com.entrhal.iqm.webapp.views.DashboardView
import com.entrhal.iqm.webapp.views.NotFoundView
import com.er453r.autodash.App.Companion.routing
import com.er453r.autodash.App.Companion.styles
import com.er453r.autodash.events.PipelineClicked
import com.er453r.autodash.utils.async
import com.er453r.autodash.utils.init
import com.er453r.autodash.utils.logger
import com.er453r.autodash.utils.on
import io.kvision.html.div
import io.kvision.panel.DockPanel
import io.kvision.panel.stackPanel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class MainView : DockPanel() {
    init {
        addCssStyle(styles.mainView)

        up {
            div("View Name")
        }

        center {
            div("CENTER") {
                stackPanel {
                    add(DashboardView(), route = "/dashboard")
                    add(NotFoundView(), route = "/*")
                }
            }
        }

        down {
            div("DOWN")
        }

        left {
            add(QueueView())
        }

        logger.init()

        logger.info("This is an info test")
        logger.warn("This is a warning test")
        logger.error("This is an error test")

        async {
            delay(2.seconds)

            logger.info("Disabling logging...")
            logger.enabled = false

            logger.info("This is an info test")
            logger.warn("This is a warning test")
            logger.error("This is an error test")

            delay(2.seconds)

            logger.info("Enabling logging...")
            logger.enabled = true

            logger.info("This is an info test")
            logger.warn("This is a warning test")
            logger.error("This is an error test")
        }

        routing.navigate("/dashboard")

        on<PipelineClicked>{
            logger.info("Clicked pipeline $it")
        }
    }
}
