package com.er453r.autodash.utils

import kotlin.reflect.KClass

val listeners = mutableListOf<EventListener<*>>()

inline fun <reified T> on(noinline callback: (T) -> Unit) {
    listeners.add(EventListener(T::class, callback))
}

fun <T> dispatch(event: T) = listeners
    .filter { it.klass.isInstance(event) }
    .forEach { (it.unsafeCast<EventListener<T>>()).callback(event) }

class EventListener<T>(
    val klass: KClass<*>,
    val callback: (T) -> Unit
)
