package com.iwdael.player.callback

import com.iwdael.player.State

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
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