package com.iwdael.player.touch

import android.content.pm.ActivityInfo
import android.view.View
import com.iwdael.player.callback.UiCallback
import com.iwdael.player.compat.getActivity
import com.iwdael.player.compat.isLandscape

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
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