package com.hacknife.player.callback

import com.hacknife.player.State

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface MediaCallback {
    fun onSeek()
    fun onSeekComplete()
    fun onPreparing()
    fun onPrepared()
    fun onPlaying()
    fun onPause()
    fun onPlayCompletion()
    fun onBufferingUpdate(progress: Int)
    fun onError(what: Int, extra: Int)
    fun onInfo(what: Int, extra: Int)
    fun onVideoSizeChanged(width: Int, height: Int)
    fun onRelease()
    fun state(): State
}