package com.iwdael.player.touch

import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.iwdael.player.IPlayer
import com.iwdael.player.callback.UiCallback
import com.iwdael.player.compat.getActivity
import kotlin.math.roundToInt

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class TouchMoveListener(private val uiCallback: UiCallback) : TouchListener {
    private val contentView =
        uiCallback.context().getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
    private val params = uiCallback.layoutParams()
    private var moveX = 0f
    private var moveY = 0f
    override fun onTouch(e: MotionEvent, width: Int, height: Int) {
        val x = e.rawX
        val y = e.rawY
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                moveX = x
                moveY = y
            }
            MotionEvent.ACTION_MOVE -> {
                if (contentView == null) return
                val movedX = x - moveX
                val movedY = y - moveY
                params.rightMargin = (params.rightMargin - movedX).roundToInt()
                params.topMargin = (params.topMargin + movedY).roundToInt()
                contentView.updateViewLayout(uiCallback.view(), params)
                moveX = x
                moveY = y
            }
        }
    }
}