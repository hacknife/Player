package com.hacknife.player.touch

import android.view.MotionEvent
import android.view.View
import com.hacknife.player.callback.UiCallback

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class TouchSettingListener(private val uiCallback: UiCallback) : View.OnTouchListener {


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (!uiCallback.isUsed()) return false
        uiCallback.onDialogSettingShow()
        return false
    }
}