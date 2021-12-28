package com.iwdael.player.callback

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.iwdael.player.IPlayer
import com.iwdael.player.Mode
import com.iwdael.player.State

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface UiCallback {
    fun onStateChangeToNormal()
    fun onStateChangeToPreparing()
    fun onStateChangeToPrepared()
    fun onStateChangeToPlaying()
    fun onStateChangeToPlayingToggle()
    fun onStateChangeToPlayComplete()
    fun onStateChangeToPause()
    fun onStateChangeToPauseToggle()
    fun onStateChangeToError()
    fun onChangeInDuration(rise: Boolean, seek: Int, max: Int)
    fun onFinishInDuration(rise: Boolean, seek: Int, max: Int)
    fun onChangeInVolume(rise: Boolean, seek: Int, max: Int)
    fun onFinishInVolume(rise: Boolean, seek: Int, max: Int)
    fun onChangeInBrightness(rise: Boolean, seek: Int, max: Int)
    fun onFinishInBrightness(rise: Boolean, seek: Int, max: Int)
    fun onDialogSettingShow()
    fun onDialogSettingDismiss()

    fun isUsed(): Boolean
    fun state(): State
    fun play()
    fun pause()
    fun context(): Context
    fun layoutParams(): FrameLayout.LayoutParams
    fun view(): View
    fun seekTo(seek: Long)
    fun getCurDuration(): Long
    fun getTotalDuration(): Long
    fun mode(): Mode
    fun width(): Int
    fun height(): Int
    fun previousPlayer(): IPlayer?
}