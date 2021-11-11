package com.hacknife.player.touch

import android.view.MotionEvent

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface TouchGesture {
    fun onTouch(e: MotionEvent, width: Int, height: Int)
}