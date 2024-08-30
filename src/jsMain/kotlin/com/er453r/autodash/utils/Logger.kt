package com.er453r.autodash.utils

external class Logger {
    @JsName("ktjsdebug")
    fun info(message: String)
}

@JsName("window")
external val logger: Logger

fun Logger.init(){
    js("window.ktjsdebug = window.console.log.bind(window.console, '%s')")
}
