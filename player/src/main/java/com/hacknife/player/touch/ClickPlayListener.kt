package com.hacknife.player.touch

import android.view.View
import com.hacknife.player.*
import com.hacknife.player.callback.MediaCallback
import com.hacknife.player.callback.UiCallback

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class ClickPlayListener(private val uiCallback: UiCallback) : ClickPlayerListener {
    override fun clear() {
    }

    override fun onClick(v: View?) {
        if (!uiCallback.isUsed()) return
        if (uiCallback.state() != State.PLAYER_STATE_PLAYING) uiCallback.play() else uiCallback.pause()
    }
}