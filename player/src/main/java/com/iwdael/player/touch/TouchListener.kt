package com.iwdael.player.touch

import android.view.MotionEvent

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface TouchListener {
    fun onTouch(e: MotionEvent, width: Int, height: Int)
}