package com.hacknife.player.compat

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */

fun Long.toTime(): String {
    val time = this / 1000
    return when {
        time <= 0 -> "00:00"
        time < 3600 -> "${String.format("%02d", time / 60)}:" +
                String.format("%02d", time % 60)
        else -> "${String.format("%02d", time / 3600)}:" +
                "${String.format("%02d", (time - ((time / 3600) * 3600)) / 60)}:" +
                String.format("%02d", time % 60)
    }
}

fun getLong(call: (() -> Long)): Long {
    return try {
        call.invoke()
    } catch (e: Exception) {
        1L
    }
}