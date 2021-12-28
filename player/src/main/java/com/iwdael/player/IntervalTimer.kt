package com.iwdael.player

import com.iwdael.player.compat.mainHandler
import java.util.*

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
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