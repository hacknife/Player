package com.iwdael.player.touch

import android.view.MotionEvent
import android.view.View
import com.iwdael.player.callback.UiCallback

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
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