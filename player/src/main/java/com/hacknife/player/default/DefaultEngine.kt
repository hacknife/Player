package com.hacknife.player.default

import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.Surface
import com.hacknife.player.*
import com.hacknife.player.callback.MediaCallback
import com.hacknife.player.compat.mainHandler
import com.hacknife.player.compat.value

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */

class DefaultEngine(callback: MediaCallback, url: Url) :
    Engine(callback, url),
    MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener,
    MediaPlayer.OnVideoSizeChangedListener {
    private val mediaPlayer = MediaPlayer()
 

    override fun preloading() {

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.isLooping = url.isLoop()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnBufferingUpdateListener(this)
        mediaPlayer.setScreenOnWhilePlaying(true)
        mediaPlayer.setOnSeekCompleteListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnInfoListener(this)
        mediaPlayer.setOnVideoSizeChangedListener(this)
    }

    override fun prepare() {
        try {
            mainHandler.post { callback.get().onPreparing() }
            val clazz: Class<MediaPlayer> = MediaPlayer::class.java
            val method = clazz.getDeclaredMethod(
                "setDataSource",
                String::class.java,
                MutableMap::class.java
            )
//            method.invoke(
//              mediaPlayer,
//                url.getCurrentUrl().toString() ,
//                url.header
//            )
            mediaPlayer.setDataSource(url.getCurrentUrl() as AssetFileDescriptor)
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
            mainHandler.post { callback.get().onError(0, 0) }
        }
    }

    override fun start() {
        mediaPlayer.start()
        mainHandler.post { callback.get().onPlaying() }
    }

    override fun pause() {
        mediaPlayer.pause()
        mainHandler.post { callback.get().onPause() }
    }

    override fun isPlaying() = mediaPlayer.isPlaying

    override fun seekTo(time: Long) {
        try {
            mainHandler.post { callback.get().onPreparing() }
            mediaPlayer.seekTo(time.toInt())
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    override fun release() {
        mainHandler.post { callback.get().onRelease() }
        mediaPlayer.release()
    }

    override fun getCurDuration() = mediaPlayer.currentPosition.toLong()
    override fun getTotalDuration(): Long = mediaPlayer.duration.toLong()

    override fun setSurface(surface: Surface) {
        mediaPlayer.setSurface(surface)
    }

    override fun setVolume(leftVolume: Float, rightVolume: Float) {
        mediaPlayer.setVolume(leftVolume, rightVolume)
    }

    override fun setSpeed(speed: Float) {
        val pp = mediaPlayer.playbackParams
        pp.speed = speed
        mediaPlayer.playbackParams = pp
    }

    override fun onSeekComplete(mp: MediaPlayer) {
        mainHandler.post { callback.get().onSeekComplete() }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mainHandler.post { callback.get().onPrepared() }
        start()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mainHandler.post { callback.get().onPlayCompletion() }
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        mainHandler.post {
            callback.get().onBufferingUpdate((percent * 0.01f * mediaPlayer.duration).toInt())
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        callback.get().onError(what, extra)
        return true
    }

    override fun onInfo(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            if (callback.get().state() === State.PLAYER_STATE_PREPARING || callback.get()
                    .state() === State.PLAYER_STATE_PREPARING_CHANGING_URL
            ) {
                callback.get().onPrepared()
            }
        } else {
            callback.get().onInfo(what, extra)
        }
        return true
    }

    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
        callback.get().let { mainHandler.post { it.onVideoSizeChanged(width, height) } }
    }


}