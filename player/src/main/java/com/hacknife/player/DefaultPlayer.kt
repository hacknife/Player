package com.hacknife.player

import android.content.Context
import android.util.AttributeSet
import com.hacknife.player.compat.*
import com.hacknife.player.touch.*

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
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
        dialogControl.showDialog(R.layout.default_dialog_player_settings, 15000) {

        }
    }

    override fun onDialogSettingDismiss() {
        dialogControl.dismissDialog(R.layout.default_dialog_player_settings)
    }

}