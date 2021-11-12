package com.hacknife.player


import android.view.Surface
import com.hacknife.player.callback.MediaCallback
import com.hacknife.player.compat.engineContainer

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : 驱动Media
 * version: 1.0
 */
abstract class Engine(callback1: MediaCallback, val url: Url) {
    val callback = Wrapper(callback1) { o, _ -> reDrive(o?.state() ?: State.PLAYER_STATE_NORMAL) }
    private fun reDrive(state: State) {
        when (state) {
            State.PLAYER_STATE_NORMAL -> {
            }
            State.PLAYER_STATE_PREPARING -> {
                callback.get().onPreparing()
            }
            State.PLAYER_STATE_PREPARING_CHANGING_URL -> {
                callback.get().onPreparing()
            }
            State.PLAYER_STATE_PREPARING_SEEKING -> {
                callback.get().onPreparing()
            }
            State.PLAYER_STATE_PREPARED -> {
                callback.get().onPrepared()
            }
            State.PLAYER_STATE_PLAYING -> {
                callback.get().onPreparing()
                callback.get().onPrepared()
                callback.get().onPlaying()
            }
            State.PLAYER_STATE_PAUSE -> {
                callback.get().onPreparing()
                callback.get().onPrepared()
                callback.get().onPause()
            }
            State.PLAYER_STATE_PLAY_COMPLETE -> {
                callback.get().onPreparing()
                callback.get().onPrepared()
                callback.get().onPlayCompletion()
            }
            State.PLAYER_STATE_ERROR -> {
                callback.get().onError(0, 0)
            }
        }
    }

    abstract fun prepare()

    abstract fun start()

    abstract fun pause()

    abstract fun isPlaying(): Boolean

    abstract fun seekTo(time: Long)

    abstract fun release()

    abstract fun getCurDuration(): Long

    abstract fun getTotalDuration(): Long

    abstract fun setSurface(surface: Surface)

    abstract fun setSpeed(speed: Float)

    abstract fun getSpeed(): Float
}