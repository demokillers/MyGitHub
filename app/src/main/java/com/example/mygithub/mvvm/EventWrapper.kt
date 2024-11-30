package com.example.mygithub.mvvm

import androidx.lifecycle.Observer

open class EventWrapper<out T>(private val content: T) {

    var hasBeenHandled = false
        internal set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}


/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Boolean) :
    Observer<EventWrapper<T>> {

    override fun onChanged(value: EventWrapper<T>) {
        value.getContentIfNotHandled()?.let {
            value.hasBeenHandled = onEventUnhandledContent(it)
        }
    }
}

class SmartObserver<T>(
    private var ignoreCreationChanged: Boolean = true,
    private val action: (it: T) -> Unit
) : Observer<T> {

    override fun onChanged(t: T) {
        if (ignoreCreationChanged) {
            ignoreCreationChanged = false
            return
        }
        action(t)
    }
}