package com.iwdael.player.touch

import android.view.View
import com.iwdael.player.*
import com.iwdael.player.callback.MediaCallback
import com.iwdael.player.callback.UiCallback

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
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