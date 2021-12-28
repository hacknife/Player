package com.iwdael.player

import android.content.Context
import android.media.AudioManager
import com.iwdael.player.compat.setBrightness

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class Display(private val context: Context) {
    private val audioManager by lazy { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    fun setVolume(volume: Int) {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)
    }

    fun setBrightness(bright: Int) {
        context.setBrightness(bright)
    }
}