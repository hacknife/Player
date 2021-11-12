package com.hacknife.player.touch

import android.view.MotionEvent
import android.view.View
import com.hacknife.player.Mode
import com.hacknife.player.State
import com.hacknife.player.callback.UiCallback

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class TouchPlayerListener(private val uiCallback: UiCallback) : View.OnTouchListener {
    private val touchMove by lazy { TouchMoveListener(uiCallback) }
    private val touchFull by lazy { TouchFullListener(uiCallback) }


    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (!uiCallback.isUsed()) return false
        if (uiCallback.mode() == Mode.FULL && uiCallback.state() >= State.PLAYER_STATE_PREPARED) {
            touchFull.onTouch(event, uiCallback.width(), uiCallback.height())
        }
        if (uiCallback.mode() == Mode.WINDOW)
            touchMove.onTouch(event, uiCallback.width(), uiCallback.height())
        return false
    }
}