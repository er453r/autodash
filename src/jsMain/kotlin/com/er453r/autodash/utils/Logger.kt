package com.er453r.autodash.utils

external class Logger {
    @JsName("ktjsloggerinfo")
    fun info(message: String)

    @JsName("ktjsloggerwarn")
    fun warn(message: String)

    @JsName("ktjsloggererror")
    fun error(message: String)
}

@JsName("window")
external val logger: Logger

@Suppress("UnusedReceiverParameter")
fun Logger.init() {
    js("window.ktjsloggerinfo = window.console.log.bind(window.console, '%c%s%c  [INFO] %c%s', 'color:dimgrey;', new Date().toISOString(), 'color:initial;', 'color:initial;')")
    js("window.ktjsloggerwarn = window.console.log.bind(window.console, '%c%s%c  [WARN] %c%s', 'color:dimgrey;', new Date().toISOString(), 'color:gold;', 'color:initial;')")
    js("window.ktjsloggererror = window.console.log.bind(window.console, '%c%s%c [ERROR] %c%s', 'color:dimgrey;', new Date().toISOString(), 'color:lightcoral;', 'color:initial;')")
}
