package com.iwdael.player.touch

import android.view.MotionEvent
import android.view.View
import com.iwdael.player.Mode
import com.iwdael.player.State
import com.iwdael.player.callback.UiCallback

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
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