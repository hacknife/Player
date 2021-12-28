package com.iwdael.player

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.iwdael.player.compat.*
import com.iwdael.player.touch.*

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class DefaultPlayer(context: Context, attrs: AttributeSet?) : IPlayer(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val dialogControl = DialogControl(context)
    override val clickZoomButton = ClickZoomListener(createCallback)
    override val clickWindowButton = ClickWindowListener(createCallback)
    override val clickErrorButton = ClickErrorListener(uiCallback)
    override val clickRotateButton = ClickRotateListener(uiCallback)
    override val clickPlayerSurface = ClickSurfaceListener(uiCallback)
    override val clickPlayButton = ClickPlayListener(uiCallback)
    override val touchSettingButton = TouchSettingListener(uiCallback)
    override val touchPlayerSurface = TouchPlayerListener(uiCallback)
    override val seekDuration = SeekDurationListener(uiCallback)

    init {
        bindClick(R.id.ivPlay, clickPlayButton)
        bindClick(R.id.ivZoom, clickZoomButton)
        bindClick(R.id.ivWindow, clickWindowButton)
        bindClick(R.id.ivBack, clickZoomButton)
        bindTouch(R.id.playerSurface, touchPlayerSurface)
        bindSeekBar(R.id.sbProgress, seekDuration)
        bindClick(R.id.playerSurface, clickPlayerSurface)
        bindClick(R.id.ivError, clickErrorButton)
        bindClick(R.id.ivRotate, clickRotateButton)
        bindTouch(R.id.ivSetting, touchSettingButton)
    }

    override fun layoutResource() = R.layout.default_player_standard

    override fun createIPlayer() = DefaultPlayer(context)

    override fun onStateChangeToNormal(mode: Mode) {
        when (mode) {
            Mode.NORMAL -> {
                this.setVisibility(R.id.titleBar, false)
                this.setVisibility(R.id.bottomBar, false)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, false)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, true)

                this.setVisibility(R.id.ivZoom, enableEnlarge)
                this.setSelected(R.id.ivZoom, false)
                this.setVisibility(R.id.ivWindow, enableWindow)
                this.setSelected(R.id.ivWindow, false)

                this.setVisibility(R.id.ivBack, false)
                this.setVisibility(R.id.ivSetting, false)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
                this.setVisibility(R.id.ivRotate, false)
            }
            Mode.FULL -> {
                this.setVisibility(R.id.titleBar, true)
                this.setVisibility(R.id.bottomBar, false)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, false)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, true)

                this.setVisibility(R.id.ivBack, true)
                if (uiCallback.previousPlayer() != null) {
                    this.setVisibility(R.id.ivZoom, enableEnlarge)
                    this.setSelected(R.id.ivZoom, true)
                    this.setVisibility(R.id.ivWindow, false)
                    this.setSelected(R.id.ivWindow, false)
                    this.setVisibility(R.id.ivRotate, false)
                } else {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, false)
                    this.setSelected(R.id.ivWindow, false)
                    this.setVisibility(R.id.ivRotate, true)
                    this.setSelected(R.id.ivRotate, context.isLandscape())
                }
                this.setVisibility(R.id.ivSetting, true)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
            }
            Mode.WINDOW -> {
                this.setVisibility(R.id.titleBar, false)
                this.setVisibility(R.id.bottomBar, false)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, false)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, true)
                if (uiCallback.previousPlayer() != null) {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, true)
                    this.setSelected(R.id.ivWindow, true)
                } else {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, true)
                    this.setSelected(R.id.ivWindow, true)
                }
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
                this.setVisibility(R.id.ivRotate, false)
            }

        }
    }

    override fun onStateChangeToPreparing(mode: Mode) {
        this.setVisibility(R.id.titleBar, false)
        this.setVisibility(R.id.bottomBar, false)
        this.setVisibility(R.id.ivPlay, false)
        this.setSelected(R.id.ivPlay, false)
        this.setVisibility(R.id.ivLoading, true)
        this.setVisibility(R.id.ivError, false)
        this.setVisibility(R.id.tvError, false)
        this.setVisibility(R.id.ivRotate, false)
    }

    override fun onStateChangeToPrepared(mode: Mode) {
        this.setVisibility(R.id.titleBar, false)
        this.setVisibility(R.id.bottomBar, false)
        this.setVisibility(R.id.ivPlay, true)
        this.setSelected(R.id.ivPlay, false)
        this.setVisibility(R.id.ivLoading, false)
        this.setVisibility(R.id.ivError, false)
        this.setVisibility(R.id.tvError, false)
        this.setVisibility(R.id.ivRotate, false)
    }

    override fun onStateChangeToPlaying(mode: Mode) {
        this.setVisibility(R.id.titleBar, false)
        this.setVisibility(R.id.bottomBar, false)
        this.setVisibility(R.id.ivPlay, false)
        this.setSelected(R.id.ivPlay, true)
        this.setVisibility(R.id.ivLoading, false)
        this.setVisibility(R.id.ivThumbnail, false)
        this.setVisibility(R.id.ivError, false)
        this.setVisibility(R.id.tvError, false)
        this.setVisibility(R.id.ivRotate, false)
    }

    override fun onStateChangeToPlayingToggle(mode: Mode) {
        when (mode) {
            Mode.NORMAL -> {
                this.setVisibility(R.id.titleBar, true)
                this.setVisibility(R.id.bottomBar, true)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, true)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, false)

                this.setVisibility(R.id.ivZoom, enableEnlarge)
                this.setSelected(R.id.ivZoom, false)
                this.setVisibility(R.id.ivWindow, enableWindow)
                this.setSelected(R.id.ivWindow, false)

                this.setVisibility(R.id.ivBack, false)
                this.setVisibility(R.id.ivSetting, false)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
                this.setVisibility(R.id.ivRotate, false)
            }
            Mode.FULL -> {
                this.setVisibility(R.id.titleBar, true)
                this.setVisibility(R.id.bottomBar, true)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, true)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, false)
                this.setVisibility(R.id.ivBack, true)

                if (uiCallback.previousPlayer() != null) {
                    this.setVisibility(R.id.ivZoom, enableEnlarge)
                    this.setSelected(R.id.ivZoom, true)
                    this.setVisibility(R.id.ivWindow, false)
                    this.setSelected(R.id.ivWindow, false)
                    this.setVisibility(R.id.ivRotate, false)
                } else {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, false)
                    this.setSelected(R.id.ivWindow, false)
                    this.setVisibility(R.id.ivRotate, true)
                    this.setSelected(R.id.ivRotate, context.isLandscape())
                }
                this.setVisibility(R.id.ivSetting, true)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)

            }
            Mode.WINDOW -> {
                this.setVisibility(R.id.titleBar, true)
                this.setVisibility(R.id.ivSetting, false)
                this.setVisibility(R.id.ivBack, false)
                this.setVisibility(R.id.tvTitle, false)

                if (uiCallback.previousPlayer() != null) {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, enableWindow)
                    this.setSelected(R.id.ivWindow, true)
                } else {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, true)
                    this.setSelected(R.id.ivWindow, true)
                }

                this.setVisibility(R.id.bottomBar, false)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, true)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, false)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
                this.setVisibility(R.id.ivRotate, false)
            }

        }
    }

    override fun onStateChangeToPlayComplete(mode: Mode) {
        this.setVisibility(R.id.titleBar, false)
        this.setVisibility(R.id.bottomBar, false)
        this.setVisibility(R.id.ivPlay, true)
        this.setSelected(R.id.ivPlay, false)
        this.setVisibility(R.id.ivLoading, false)
        this.setVisibility(R.id.ivThumbnail, false)
        this.setVisibility(R.id.ivError, false)
        this.setVisibility(R.id.tvError, false)
        this.setVisibility(R.id.ivRotate, false)
    }

    override fun onStateChangeToPause(mode: Mode, isTextureUpdated: Boolean) {
        this.setVisibility(R.id.titleBar, false)
        this.setVisibility(R.id.bottomBar, false)
        this.setVisibility(R.id.ivPlay, true)
        this.setSelected(R.id.ivPlay, false)
        this.setVisibility(R.id.ivLoading, false)
        if (isTextureUpdated)
            this.setVisibility(R.id.ivThumbnail, false)
        else
            this.setVisibility(R.id.ivThumbnail, true)
        this.setVisibility(R.id.ivError, false)
        this.setVisibility(R.id.tvError, false)
        this.setVisibility(R.id.ivRotate, false)
    }

    override fun onStateChangeToPauseToggle(mode: Mode, isTextureUpdated: Boolean) {
        when (mode) {
            Mode.NORMAL -> {
                this.setVisibility(R.id.titleBar, true)
                this.setVisibility(R.id.bottomBar, true)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, false)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, false)

                this.setVisibility(R.id.ivZoom, enableEnlarge)
                this.setSelected(R.id.ivZoom, false)
                this.setVisibility(R.id.ivWindow, enableWindow)
                this.setSelected(R.id.ivWindow, false)

                this.setVisibility(R.id.ivBack, false)
                this.setVisibility(R.id.ivSetting, false)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
                this.setVisibility(R.id.ivRotate, false)
            }

            Mode.FULL -> {
                this.setVisibility(R.id.titleBar, true)
                this.setVisibility(R.id.bottomBar, true)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, false)
                this.setVisibility(R.id.ivLoading, false)
                if (isTextureUpdated)
                    this.setVisibility(R.id.ivThumbnail, false)
                else
                    this.setVisibility(R.id.ivThumbnail, true)
                this.setVisibility(R.id.ivBack, true)

                if (uiCallback.previousPlayer() != null) {
                    this.setVisibility(R.id.ivZoom, enableEnlarge)
                    this.setSelected(R.id.ivZoom, true)
                    this.setVisibility(R.id.ivWindow, false)
                    this.setSelected(R.id.ivWindow, false)
                    this.setVisibility(R.id.ivRotate, false)
                } else {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, false)
                    this.setSelected(R.id.ivWindow, false)
                    this.setVisibility(R.id.ivRotate, true)
                    this.setSelected(R.id.ivRotate, context.isLandscape())
                }

                this.setVisibility(R.id.ivSetting, true)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
            }
            Mode.WINDOW -> {
                this.setVisibility(R.id.titleBar, true)
                this.setVisibility(R.id.ivSetting, false)
                this.setVisibility(R.id.ivBack, false)
                this.setVisibility(R.id.tvTitle, false)

                if (uiCallback.previousPlayer() != null) {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, enableWindow)
                    this.setSelected(R.id.ivWindow, true)
                } else {
                    this.setVisibility(R.id.ivZoom, false)
                    this.setSelected(R.id.ivZoom, false)
                    this.setVisibility(R.id.ivWindow, false)
                    this.setSelected(R.id.ivWindow, false)
                }

                this.setVisibility(R.id.bottomBar, false)
                this.setVisibility(R.id.ivPlay, true)
                this.setSelected(R.id.ivPlay, false)
                this.setVisibility(R.id.ivLoading, false)
                this.setVisibility(R.id.ivThumbnail, false)
                this.setVisibility(R.id.ivError, false)
                this.setVisibility(R.id.tvError, false)
                this.setVisibility(R.id.ivRotate, false)
            }
        }
    }

    override fun onStateChangeToError(mode: Mode) {
        this.setVisibility(R.id.titleBar, false)
        this.setVisibility(R.id.bottomBar, false)
        this.setVisibility(R.id.ivPlay, false)
        this.setVisibility(R.id.ivLoading, false)
        this.setVisibility(R.id.ivError, true)
        this.setVisibility(R.id.tvError, true)
        this.setVisibility(R.id.ivRotate, false)
    }

    override fun onChangeInDuration(rise: Boolean, seek: Int, max: Int) {
        dialogControl.showDialog(R.layout.default_dialog_player_duration) {
            it.setDrawable(
                R.id.ivMode,
                if (rise) R.drawable.default_dialog_player_mode_duration_forward else R.drawable.default_dialog_player_mode_duration_backward
            )
            it.setText(R.id.tvCurrent, seek.toLong().toTime())
            it.setText(R.id.tvTotal, max.toLong().toTime())
            it.setProgressMax(R.id.progressbar, max)
            it.setProgress(R.id.progressbar, seek)
        }
    }

    override fun onFinishInDuration(rise: Boolean, seek: Int, max: Int) {
        dialogControl.dismissDialog(R.layout.default_dialog_player_duration)
    }

    override fun onChangeInVolume(rise: Boolean, seek: Int, max: Int) {
        dialogControl.showDialog(R.layout.default_dialog_player_volume) {
            it.setDrawable(
                R.id.ivMode,
                if (seek == 0) R.drawable.default_dialog_player_mode_volume_desc else R.drawable.default_dialog_player_mode_volume_rise
            )
            it.setText(R.id.tvCurrent, "${((seek * 100f) / max).toInt()}%")
            it.setProgressMax(R.id.progressbar, max)
            it.setProgress(R.id.progressbar, seek)
        }
    }

    override fun onFinishInVolume(rise: Boolean, seek: Int, max: Int) {
        dialogControl.dismissDialog(R.layout.default_dialog_player_volume)
    }


    override fun onChangeInBrightness(rise: Boolean, seek: Int, max: Int) {
        dialogControl.showDialog(R.layout.default_dialog_player_brightness) {
            it.setText(R.id.tvCurrent, "${((seek * 100f) / max).toInt()}%")
            it.setProgressMax(R.id.progressbar, max)
            it.setProgress(R.id.progressbar, seek)
        }
    }

    override fun onFinishInBrightness(rise: Boolean, seek: Int, max: Int) {
        dialogControl.dismissDialog(R.layout.default_dialog_player_brightness)
    }


    override fun onDialogSettingShow() {
        dialogControl.showDialog(R.layout.default_dialog_player_settings, 8000) {
            it.setSelected(R.id.tvRotation0, getDisplayRotation() == 0f)
            it.setSelected(R.id.tvRotation90, getDisplayRotation() == 90f)
            it.setSelected(R.id.tvRotation180, getDisplayRotation() == 180f)
            it.setSelected(R.id.tvRotation270, getDisplayRotation() == 270f)
            it.setSelected(R.id.ivRotation0, getDisplayRotation() == 0f)
            it.setSelected(R.id.ivRotation90, getDisplayRotation() == 90f)
            it.setSelected(R.id.ivRotation180, getDisplayRotation() == 180f)
            it.setSelected(R.id.ivRotation270, getDisplayRotation() == 270f)

            it.setSelected(R.id.tvSizeOrigin, getDisplayType() == SurfaceType.SCREEN_TYPE_ORIGINAL)
            it.setSelected(R.id.tvSizeFit, getDisplayType() == SurfaceType.SCREEN_TYPE_FIT_XY)
            it.setSelected(R.id.tvSizeCrop, getDisplayType() == SurfaceType.SCREEN_TYPE_CENTER_CROP)
            it.setSelected(R.id.tvSizeNormal, getDisplayType() == SurfaceType.SCREEN_TYPE_NORMAL)

            it.setSelected(R.id.tvSpeed05, getSpeed() == 0.5f)
            it.setSelected(R.id.tvSpeed10, getSpeed() == 1.0f)
            it.setSelected(R.id.tvSpeed20, getSpeed() == 2.0f)
            it.setSelected(R.id.tvSpeed30, getSpeed() == 3.0f)


            it.setTouch(R.id.tvRotation0) {
                setDisplayRotation(0f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvRotation90) {
                setDisplayRotation(90f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvRotation180) {
                setDisplayRotation(180f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvRotation270) {
                setDisplayRotation(270f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.ivRotation0) {
                setDisplayRotation(0f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.ivRotation90) {
                setDisplayRotation(90f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.ivRotation180) {
                setDisplayRotation(180f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.ivRotation270) {
                setDisplayRotation(270f)
                onDialogSettingShow()
            }


            it.setTouch(R.id.tvSizeOrigin) {
                setDisplayType(SurfaceType.SCREEN_TYPE_ORIGINAL)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvSizeFit) {
                setDisplayType(SurfaceType.SCREEN_TYPE_FIT_XY)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvSizeCrop) {
                setDisplayType(SurfaceType.SCREEN_TYPE_CENTER_CROP)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvSizeNormal) {
                setDisplayType(SurfaceType.SCREEN_TYPE_NORMAL)
                onDialogSettingShow()
            }

            it.setTouch(R.id.tvSpeed05) {
                setSpeed(0.5f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvSpeed10) {
                setSpeed(1f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvSpeed20) {
                setSpeed(2f)
                onDialogSettingShow()
            }
            it.setTouch(R.id.tvSpeed30) {
                setSpeed(3f)
                onDialogSettingShow()
            }

        }
    }

    override fun onDialogSettingDismiss() {
        dialogControl.dismissDialog(R.layout.default_dialog_player_settings)
    }

}