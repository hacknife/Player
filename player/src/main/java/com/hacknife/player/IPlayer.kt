package com.hacknife.player

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.util.Size
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import com.hacknife.player.callback.CreateCallback
import com.hacknife.player.callback.MediaCallback
import com.hacknife.player.callback.UiCallback
import com.hacknife.player.compat.*
import com.hacknife.player.default.DefaultEngineCreator
import com.hacknife.player.default.DefaultThumbnailLoader
import com.hacknife.player.touch.*
import com.hacknife.player.widget.SurfaceView

abstract class IPlayer(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    constructor(context: Context) : this(context, null)

    companion object {
        var engineCreator: EngineCreator = DefaultEngineCreator()
        var thumbnailLoader: ThumbnailLoader = DefaultThumbnailLoader()
    }

    private val mode = Wrapper(Mode.NORMAL)
    private val state = Wrapper(State.PLAYER_STATE_NORMAL) { _, it -> stateChangTo(it) }
    private val engine = Wrapper<Engine>()
    private val previousPlayer = Wrapper<IPlayer>()
    private val surface = SurfaceView(context)
    private var url: Url? = null
    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { }
    protected val mediaCallback: MediaCallback = object : MediaCallback {
        override fun onSeek() {
            state.set(State.PLAYER_STATE_PREPARING)
        }

        override fun onSeekComplete() {
            state.set(if (isPlaying()) State.PLAYER_STATE_PLAYING else State.PLAYER_STATE_PAUSE)
        }

        override fun onPreparing() {
            state.set(State.PLAYER_STATE_PREPARING)
        }

        override fun onPrepared() {
            state.set(State.PLAYER_STATE_PREPARED)
            setSeekMax(R.id.sbProgress, getTotalDuration().toInt())
        }

        override fun onPlaying() {
            state.set(State.PLAYER_STATE_PLAYING)
        }

        override fun onPause() {
            state.set(State.PLAYER_STATE_PAUSE)
        }

        override fun onPlayCompletion() {
            state.set(State.PLAYER_STATE_PLAY_COMPLETE)
            clickPlayerSurface.clear()
        }

        override fun onBufferingUpdate(progress: Int) {
            setSeekSecond(R.id.sbProgress, progress)
        }

        override fun onError(what: Int, extra: Int) {
            state.set(State.PLAYER_STATE_ERROR)
            clickPlayerSurface.clear()
        }

        override fun onRelease() {
            state.set(State.PLAYER_STATE_RELEASE)
            clickPlayerSurface.clear()
        }

        override fun onInfo(what: Int, extra: Int) {
        }

        override fun onVideoSizeChanged(width: Int, height: Int) {
            this@IPlayer.surface.size = Size(width, height)
        }

        override fun state() = this@IPlayer.state.get()
    }
    protected val uiCallback: UiCallback = object : UiCallback {
        override fun onStateChangeToNormal() = this@IPlayer.stateChangTo(State.PLAYER_STATE_NORMAL)
        override fun onStateChangeToPreparing() =
            this@IPlayer.stateChangTo(State.PLAYER_STATE_PREPARING)

        override fun onStateChangeToPrepared() =
            this@IPlayer.stateChangTo(State.PLAYER_STATE_PREPARED)

        override fun onStateChangeToPlaying() =
            this@IPlayer.stateChangTo(State.PLAYER_STATE_PLAYING)

        override fun onStateChangeToPlayComplete() =
            this@IPlayer.stateChangTo(State.PLAYER_STATE_PLAY_COMPLETE)

        override fun onStateChangeToPause() = this@IPlayer.stateChangTo(State.PLAYER_STATE_PAUSE)
        override fun onStateChangeToError() = this@IPlayer.stateChangTo(State.PLAYER_STATE_ERROR)
        override fun onStateChangeToPlayingToggle() =
            this@IPlayer.onStateChangeToPlayingToggle(mode.get())

        override fun onStateChangeToPauseToggle() =
            this@IPlayer.onStateChangeToPauseToggle(mode.get(), surface.isTextureUpdated)

        override fun onChangeInDuration(rise: Boolean, seek: Int, max: Int) =
            this@IPlayer.onChangeInDuration(rise, seek, max)

        override fun onFinishInDuration(rise: Boolean, seek: Int, max: Int) =
            this@IPlayer.onFinishInDuration(rise, seek, max)

        override fun onChangeInVolume(rise: Boolean, seek: Int, max: Int) =
            this@IPlayer.onChangeInVolume(rise, seek, max)

        override fun onFinishInVolume(rise: Boolean, seek: Int, max: Int) =
            this@IPlayer.onFinishInVolume(rise, seek, max)

        override fun onChangeInBrightness(rise: Boolean, seek: Int, max: Int) =
            this@IPlayer.onChangeInBrightness(rise, seek, max)

        override fun onFinishInBrightness(rise: Boolean, seek: Int, max: Int) =
            this@IPlayer.onFinishInBrightness(rise, seek, max)

        override fun isUsed() = engine.get().callback.get() == mediaCallback
        override fun state() = state.get()
        override fun play() = this@IPlayer.play()
        override fun pause() = this@IPlayer.pause()
        override fun context() = this@IPlayer.context!!
        override fun layoutParams() = this@IPlayer.layoutParams as FrameLayout.LayoutParams
        override fun view() = this@IPlayer
        override fun seekTo(seek: Long) = this@IPlayer.engine.get().seekTo(seek)
        override fun getCurDuration() = getLong { this@IPlayer.engine.get().getCurDuration() }
        override fun getTotalDuration() = getLong { this@IPlayer.engine.get().getTotalDuration() }
        override fun mode() = this@IPlayer.mode.get()
        override fun width() = this@IPlayer.width
        override fun height() = this@IPlayer.height
        override fun previousPlayer() = this@IPlayer.previousPlayer.getOrNull()
    }
    protected val createCallback: CreateCallback = object : CreateCallback {
        override fun context() = this@IPlayer.context
        override fun createPlayer() = this@IPlayer.createIPlayer().let { it to it.createCallback }
        override fun switchPlayer(player: IPlayer) = this@IPlayer.switchPlayer(player)
        override fun getCurrentPlayer() = this@IPlayer
        override fun getCurrentVideoSize() = this@IPlayer.surface.size
        override fun getPreviousPlayer() =
            this@IPlayer.previousPlayer.getOrNull()?.let { it to it.createCallback }

        override fun setMode(mode: Mode) = this@IPlayer.mode.set(mode)
        override fun getMode() = this@IPlayer.mode.get()
        override fun getState() = this@IPlayer.state.get()
        override fun setState(state: State) = this@IPlayer.state.set(state)
        override fun isUsed() =
            this@IPlayer.engine.get().callback.get() == this@IPlayer.mediaCallback
    }
    protected abstract val clickZoomButton: ClickPlayerListener
    protected abstract val clickWindowButton: ClickPlayerListener
    protected abstract val clickErrorButton: ClickPlayerListener
    protected abstract val clickRotateButton: ClickPlayerListener
    protected abstract val clickPlayerSurface: ClickPlayerListener
    protected abstract val clickPlayButton: ClickPlayerListener
    protected abstract val touchPlayerSurface: OnTouchListener
    protected abstract val seekDuration: SeekBar.OnSeekBarChangeListener
    protected var enableEnlarge: Boolean
    protected var enableWindow: Boolean

    init {
        View.inflate(context, layoutResource(), this)
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.IPlayer)
            enableEnlarge = ta.getBoolean(R.styleable.IPlayer_enableEnlarge, true)
            enableWindow = ta.getBoolean(R.styleable.IPlayer_enableWindow, true)
            when (ta.getInt(R.styleable.IPlayer_display, 1)) {
                0 -> surface.type = SurfaceType.SCREEN_TYPE_ORIGINAL
                1 -> surface.type = SurfaceType.SCREEN_TYPE_NORMAL
                2 -> surface.type = SurfaceType.SCREEN_TYPE_CENTER_CROP
                3 -> surface.type = SurfaceType.SCREEN_TYPE_FIT_XY
            }
            when (ta.getInt(R.styleable.IPlayer_mode, 0)) {
                0 -> mode.set(Mode.NORMAL)
                1 -> mode.set(Mode.FULL)
            }
            ta.recycle()
        } else {
            enableEnlarge = true
            enableWindow = true
            surface.type = SurfaceType.SCREEN_TYPE_NORMAL
        }
    }

    fun setMode(mode: Mode) {
        if (this.url != null) throw Exception("called before setDataSounrce")
        this.mode.set(mode)
    }

    fun setDataSource(url: Url) {
        this.url = url
        this.engine.set(engineCreator.create(mediaCallback, url))
        setText(R.id.tvTitle, url.getTitle())
        mode.set(mode.get())
        findViewById<ImageView>(R.id.ivThumbnail)?.let {
            thumbnailLoader.load(
                it,
                url.getThumbnail()
            )
        }
        state.set(State.PLAYER_STATE_NORMAL)
    }

    fun getDataSource() = this.url!!

    fun play() {
        if (url == null) throw IllegalArgumentException("url is null")
        if (state.get() == State.PLAYER_STATE_NORMAL || state.get() == State.PLAYER_STATE_ERROR) preparePlay() else directPlay()
        clickPlayerSurface.clear()
    }

    fun pause() {
        engine.get().pause()
        clickPlayerSurface.clear()
    }

    fun release() {
        pause()
        engine.get().release()
        context.abandonAudioFocus(audioFocusChangeListener)
    }

    fun isPlaying() = state.get() == State.PLAYER_STATE_PLAYING

    fun getTotalDuration() = getLong { engine.get().getTotalDuration() }

    fun getCurDuration() = getLong { engine.get().getCurDuration() }

    fun setDisplayRotation(rotation: Float) {
        surface.rotation = rotation
    }

    fun getDisplayRotation() = surface.rotation

    fun setDisplayType(mode: SurfaceType) {
        surface.type = mode
    }

    fun getDisplayType() = surface.type!!

    private fun directPlay() {
        engine.get().start()
    }

    private fun preparePlay() {
        context.registerApplicationLifecycle()
        EngineLifecycleCallbacks(context, this.engine.get().callback, this.engine.get()).register()
        bindSurface(engine.get(), surface)
        context.requestAudioFocus(audioFocusChangeListener)
    }

    override fun onSaveInstanceState(): Parcelable? {
        clickPlayerSurface.clear()
        val bundle = Bundle()
        val superBundle = super.onSaveInstanceState()
        val urlBundle = url?.toBundle()
        bundle.putParcelable("SUPER_BUNDLE", superBundle)
        bundle.putBoolean("ENABLE_ENLARGE", enableEnlarge)
        bundle.putBoolean("ENABLE_WINDOW", enableWindow)
        bundle.putParcelable("URL_BUNDLE", urlBundle)
        bundle.putInt("DISPLAY_TYPE", getDisplayType().value)
        bundle.putFloat("DISPLAY_ROTATION", getDisplayRotation())
        bundle.putBoolean("IS_PLAYING", isPlaying())
        bundle.putInt("DISPLAY_SIZE_WIDTH", surface.size.width)
        bundle.putInt("DISPLAY_SIZE_HEIGHT", surface.size.height)
        return bundle
    }

    override fun onRestoreInstanceState(bunlde: Parcelable) {
        bunlde as Bundle
        val superBundle = bunlde.getParcelable<Parcelable>("SUPER_BUNDLE")
        this.enableEnlarge = bunlde.getBoolean("ENABLE_ENLARGE")
        this.enableWindow = bunlde.getBoolean("ENABLE_WINDOW")
        val displayType = bunlde.getInt("DISPLAY_TYPE")
        val displayRotation = bunlde.getFloat("DISPLAY_ROTATION")
        when (displayType) {
            SurfaceType.SCREEN_TYPE_ORIGINAL.value -> setDisplayType(SurfaceType.SCREEN_TYPE_ORIGINAL)
            SurfaceType.SCREEN_TYPE_NORMAL.value -> setDisplayType(SurfaceType.SCREEN_TYPE_NORMAL)
            SurfaceType.SCREEN_TYPE_CENTER_CROP.value -> setDisplayType(SurfaceType.SCREEN_TYPE_CENTER_CROP)
            SurfaceType.SCREEN_TYPE_FIT_XY.value -> setDisplayType(SurfaceType.SCREEN_TYPE_FIT_XY)
        }
        setDisplayRotation(displayRotation)
        val width = bunlde.getInt("DISPLAY_SIZE_WIDTH")
        val height = bunlde.getInt("DISPLAY_SIZE_HEIGHT")
        surface.size = Size(width, height)
        bunlde.getBundle("URL_BUNDLE")?.let {
            this.url = Url.fromBundle(it)
            setText(R.id.tvTitle, url!!.getTitle())
            mode.set(mode.get())
            findViewById<ImageView>(R.id.ivThumbnail)?.let { thumbnail ->
                thumbnailLoader.load(thumbnail, this.url!!.getThumbnail())
            }
            this.state.set(State.PLAYER_STATE_NORMAL)
            this.engine.set(engineContainer[this.url!!.getCurrentUrl().toString()]!!)
            this.engine.get().callback.set(mediaCallback)
            this.bindSurface(engine.get(), surface)
        }
        val isPlaying = bunlde.getBoolean("IS_PLAYING")
        if (isPlaying) play()
        super.onRestoreInstanceState(superBundle)
    }

    private fun switchPlayer(player: IPlayer) {
        this.enableEnlarge = player.enableEnlarge
        this.enableWindow = player.enableWindow
        this.previousPlayer.set(player)
        this.url = player.getDataSource()
        this.setText(R.id.tvTitle, this.url!!.getTitle())
        this.state.set(State.PLAYER_STATE_NORMAL)
        this.setDisplayType(player.getDisplayType())
        this.setDisplayRotation(player.getDisplayRotation())
        this.engine.set(player.engine.get())
        this.engine.get().callback.set(mediaCallback)
        this.bindSurface(engine.get(), surface)
        player.unBindSurface()
        player.clickPlayerSurface.clear()
    }

    private fun stateChangTo(value: State) {
        when (value) {
            State.PLAYER_STATE_NORMAL -> onStateChangeToNormal(mode.get())
            State.PLAYER_STATE_PREPARING -> onStateChangeToPreparing(mode.get())
            State.PLAYER_STATE_PREPARING_CHANGING_URL -> onStateChangeToPreparing(mode.get())
            State.PLAYER_STATE_PREPARING_SEEKING -> onStateChangeToPreparing(mode.get())
            State.PLAYER_STATE_PREPARED -> onStateChangeToPrepared(mode.get())
            State.PLAYER_STATE_PLAYING -> onStateChangeToPlaying(mode.get())
            State.PLAYER_STATE_PAUSE -> onStateChangeToPause(mode.get(), surface.isTextureUpdated)
            State.PLAYER_STATE_PLAY_COMPLETE -> onStateChangeToPlayComplete(mode.get())
            State.PLAYER_STATE_ERROR -> onStateChangeToError(mode.get())
            State.PLAYER_STATE_RELEASE -> onStateChangeToRelease(mode.get())
        }
    }

    protected abstract fun layoutResource(): Int
    protected abstract fun createIPlayer(): IPlayer
    protected abstract fun onStateChangeToNormal(mode: Mode)
    protected abstract fun onStateChangeToPreparing(mode: Mode)
    protected abstract fun onStateChangeToPrepared(mode: Mode)
    protected abstract fun onStateChangeToPlaying(mode: Mode)
    protected abstract fun onStateChangeToPlayingToggle(mode: Mode)
    protected abstract fun onStateChangeToPlayComplete(mode: Mode)
    protected abstract fun onStateChangeToPause(mode: Mode, isTextureUpdated: Boolean)
    protected abstract fun onStateChangeToPauseToggle(mode: Mode, isTextureUpdated: Boolean)
    protected abstract fun onStateChangeToError(mode: Mode)
    protected open fun onStateChangeToRelease(mode: Mode) {}
    protected abstract fun onChangeInDuration(rise: Boolean, seek: Int, max: Int)
    protected abstract fun onFinishInDuration(rise: Boolean, seek: Int, max: Int)
    protected abstract fun onChangeInVolume(rise: Boolean, seek: Int, max: Int)
    protected abstract fun onFinishInVolume(rise: Boolean, seek: Int, max: Int)
    protected abstract fun onChangeInBrightness(rise: Boolean, seek: Int, max: Int)
    protected abstract fun onFinishInBrightness(rise: Boolean, seek: Int, max: Int)

}