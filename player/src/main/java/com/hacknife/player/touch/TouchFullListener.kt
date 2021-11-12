package com.hacknife.player.touch

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.STREAM_MUSIC
import android.view.MotionEvent
import com.hacknife.player.*
import com.hacknife.player.callback.UiCallback
import com.hacknife.player.compat.getBrightness
import kotlin.math.abs
import kotlin.math.roundToInt

class TouchFullListener(
    private val uiCallback: UiCallback
) : TouchListener {
    companion object {
        private const val OFFSET = 20
    }

    private val change: ((TouchMode, Boolean, Int, Int) -> Unit) = { mode, rise, value, max ->
        uiCallback.onDialogSettingDismiss()
        when (mode) {
            TouchMode.VOLUME -> {
                display.setVolume(value)
                uiCallback.onChangeInVolume(rise, value, max)

            }
            TouchMode.BRIGHTNESS -> {
                display.setBrightness(value)
                uiCallback.onChangeInBrightness(rise, value, max)

            }
            TouchMode.DURATION -> {
                uiCallback.onChangeInDuration(rise, value, max)

            }
            else -> {
            }
        }
    }
    private val end: ((TouchMode, Boolean, Int, Int) -> Unit) = { mode, rise, value, max ->
        when (mode) {
            TouchMode.VOLUME -> {
                display.setVolume(value)
                uiCallback.onFinishInVolume(rise, value, max)

            }
            TouchMode.BRIGHTNESS -> {
                display.setBrightness(value)
                uiCallback.onFinishInBrightness(rise, value, max)

            }
            TouchMode.DURATION -> {
                uiCallback.seekTo(value.toLong())
                uiCallback.onFinishInDuration(rise, value, max)

            }
            else -> {
            }
        }
    }

    private val display = Display(uiCallback.context())
    private val audioManager =
        uiCallback.context().getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private var downX = 0f
    private var downY = 0f
    private var touchMode = TouchMode.UNKNOWN
    private var downProgress = 0L
    private var downBrightness = 0
    private var downVolume = 0
    private var maxVolume = 1
    private var maxBrightness = 255f
    private var maxProgress = 0L
    private var currentValue = -1 //暂时
    override fun onTouch(e: MotionEvent, width: Int, height: Int) {
        val x = e.x
        val y = e.y
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = x
                downY = y
                touchMode = TouchMode.UNKNOWN
                currentValue = -1

                maxVolume = audioManager.getStreamMaxVolume(STREAM_MUSIC)
                maxBrightness = 255f
                maxProgress = uiCallback.getTotalDuration()

                downProgress = uiCallback.getCurDuration()
                downVolume = audioManager.getStreamVolume(STREAM_MUSIC)
                downBrightness = uiCallback.context().getBrightness()

            }
            MotionEvent.ACTION_MOVE -> {
                val offsetX = x - downX
                val offsetY = y - downY
                val absOffsetX = abs(offsetX)
                val absOffsetY = abs(offsetY)
                if (touchMode == TouchMode.UNKNOWN) {
                    if (absOffsetX >= OFFSET || absOffsetY >= OFFSET) {
                        touchMode = if (absOffsetX >= absOffsetY) {
                            TouchMode.DURATION
                        } else {
                            if (downX < (width / 2)) { //左侧
                                TouchMode.BRIGHTNESS
                            } else {//右侧
                                TouchMode.VOLUME
                            }
                        }
                    }
                }
                var tempValue = 0
                when (touchMode) {
                    TouchMode.DURATION -> {
                        tempValue = (downProgress + (offsetX * 100)).toInt()
                        if (tempValue > maxProgress)
                            tempValue = maxProgress.toInt()
                        if (tempValue < 0)
                            tempValue = 0
                    }
                    TouchMode.BRIGHTNESS -> {
                        tempValue =
                            (downBrightness + ((-offsetY * maxBrightness) / width)).roundToInt()
                        if (tempValue > maxBrightness) tempValue = maxBrightness.roundToInt()
                        else if (tempValue <= 1) tempValue = 1
                    }
                    TouchMode.VOLUME -> {
                        tempValue =
                            (downVolume + ((-offsetY * maxVolume) / width)).roundToInt()
                        if (tempValue > maxVolume) tempValue = maxVolume
                        else if (tempValue <= 0) tempValue = 0
                    }
                    else -> {
                    }
                }
                if (touchMode != TouchMode.UNKNOWN && currentValue != tempValue) {
                    when (touchMode) {
                        TouchMode.DURATION ->
                            change.invoke(touchMode, offsetX > 0, tempValue, maxProgress.toInt())
                        TouchMode.VOLUME ->
                            change.invoke(touchMode, offsetY < 0, tempValue, maxVolume)
                        TouchMode.BRIGHTNESS ->
                            change.invoke(
                                touchMode,
                                offsetY < 0,
                                tempValue,
                                maxBrightness.toInt()
                            )
                        else -> {
                        }
                    }
                }
                currentValue = tempValue
            }
            MotionEvent.ACTION_UP -> {
                val offsetX = x - downX
                val offsetY = y - downY
                var tempValue = 0
                when (touchMode) {
                    TouchMode.DURATION -> {
                        tempValue = (downProgress + (offsetX * 100)).toInt()
                        if (tempValue > maxProgress)
                            tempValue = maxProgress.toInt()
                        if (tempValue < 0)
                            tempValue = 0
                    }
                    TouchMode.BRIGHTNESS -> {
                        tempValue =
                            (downBrightness + ((-offsetY * maxBrightness) / width)).roundToInt()
                        if (tempValue > maxBrightness) tempValue = maxBrightness.roundToInt()
                        else if (tempValue <= 1) tempValue = 1
                    }
                    TouchMode.VOLUME -> {
                        tempValue =
                            (downVolume + ((-offsetY * maxVolume) / width)).roundToInt()
                        if (tempValue > maxVolume) tempValue = maxVolume
                        else if (tempValue <= 0) tempValue = 0
                    }
                    else -> {
                    }
                }
                if (touchMode != TouchMode.UNKNOWN) {
                    when (touchMode) {
                        TouchMode.DURATION ->
                            end.invoke(touchMode, offsetX > 0, tempValue, maxProgress.toInt())
                        TouchMode.VOLUME ->
                            end.invoke(touchMode, offsetY < 0, tempValue, maxVolume)
                        TouchMode.BRIGHTNESS ->
                            end.invoke(touchMode, offsetY < 0, tempValue, maxBrightness.toInt())
                        else -> {
                        }
                    }
                }
                touchMode = TouchMode.UNKNOWN
                currentValue = -1
            }
            MotionEvent.ACTION_CANCEL -> {
                touchMode = TouchMode.UNKNOWN
                currentValue = -1
            }
        }
    }

}