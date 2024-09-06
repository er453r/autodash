package com.er453r.autodash.views

import com.entrhal.iqm.webapp.views.DashboardView
import com.entrhal.iqm.webapp.views.NotFoundView
import com.er453r.autodash.App.Companion.routing
import com.er453r.autodash.App.Companion.styles
import com.er453r.autodash.events.PipelineClicked
import com.er453r.autodash.utils.init
import com.er453r.autodash.utils.logger
import com.er453r.autodash.utils.on
import io.kvision.html.div
import io.kvision.panel.DockPanel
import io.kvision.panel.stackPanel

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

        routing.navigate("/dashboard")

        on<PipelineClicked>{
            logger.info("Clicked pipeline $it")
        }
    }
}
