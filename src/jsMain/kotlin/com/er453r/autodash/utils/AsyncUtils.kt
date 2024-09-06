package com.er453r.autodash.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun async(block: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.Default).launch(block = block)
}
