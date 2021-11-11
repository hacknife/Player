package com.hacknife.player.touch

import android.content.pm.ActivityInfo
import android.view.View
import com.hacknife.player.callback.UiCallback
import com.hacknife.player.compat.getActivity
import com.hacknife.player.compat.isLandscape

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class ClickRotateListener(private val uiCallback: UiCallback) : ClickPlayerListener {
    private var activity = uiCallback.context().getActivity()
    override fun clear() {
    }

    override fun onClick(v: View) {
        if (activity == null) return
        if (uiCallback.context().isLandscape())
            activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else
            activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}