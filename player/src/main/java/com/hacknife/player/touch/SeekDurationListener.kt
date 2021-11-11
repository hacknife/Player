package com.hacknife.player.touch

import android.util.Log
import android.widget.SeekBar
import com.hacknife.player.callback.UiCallback

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class SeekDurationListener(private val uiCallback: UiCallback) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        if (uiCallback.isUsed()) uiCallback.seekTo(seekBar.progress.toLong())
    }
}