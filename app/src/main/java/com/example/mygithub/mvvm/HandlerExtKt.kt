package com.example.mygithub.mvvm

import android.os.Handler
import android.os.Looper

val uiHandler by lazy {
    Handler(Looper.getMainLooper())
}

fun runOnUI(action: () -> Unit) {
    if (isMainThread()) {
        action.invoke()
    } else {
        uiHandler.post(action)
    }
}

fun runOnUIDelay(delay: Long, action: () -> Unit) {
    uiHandler.postDelayed(action, delay)
}

fun removeCallbacks(runnable: Runnable) {
    uiHandler.removeCallbacks(runnable)
}

inline fun isMainThread(): Boolean {
    return Looper.getMainLooper() == Looper.myLooper()
}