package com.hacknife.player

import com.hacknife.player.compat.mainHandler
import java.util.*

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class IntervalTimer {
    private var task: TimerTask? = null
    private var timer: Timer? = null

    fun interval(interval: Long, call: (() -> Unit)) {
        clear()
        task = object : TimerTask() {
            override fun run() {
                mainHandler.post { call.invoke() }
            }
        }
        timer = Timer()
        timer?.schedule(task, 0, interval)
    }

    fun clear() {
        task?.cancel()
        timer?.cancel()
        task = null
        timer = null
    }
}