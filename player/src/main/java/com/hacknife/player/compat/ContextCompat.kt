package com.hacknife.player.compat

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Rect
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Size
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import com.hacknife.player.*
import java.lang.reflect.Constructor
import kotlin.math.roundToInt

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */


fun Context.getWindow(): Window? {
    return getActivity()?.window
}

fun Context.getActivity(): Activity? {
    return if (getAppCompatActivity() != null) {
        getAppCompatActivity()
    } else {
        getAppActivity()
    }
}


fun Context?.getAppCompatActivity(): AppCompatActivity? {
    if (this is AppCompatActivity) {
        return this
    } else if (this is ContextThemeWrapper) {
        return this.baseContext?.getAppCompatActivity()
    }
    return null
}

fun Context.getAppActivity(): Activity? {
    if (this is Activity) {
        return this
    } else if (this is ContextWrapper) {
        return this.baseContext?.getAppActivity()
    }
    return null
}

fun Context.getBrightness(): Int {
    this.getWindow()?.let {
        val screenBrightness = it.attributes.screenBrightness
        if (screenBrightness < 0) return@let
        if (screenBrightness > 1) return 255
        return (screenBrightness * 255).roundToInt()
    }
    return try {
        val screenBrightness =
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        when {
            screenBrightness < 0 -> 0
            screenBrightness > 255 -> 255
            else -> screenBrightness
        }
    } catch (e: SettingNotFoundException) {
        125
    }
}


fun Context.setBrightness(brightness: Int) {
    this.getWindow()?.let {
        val attributes = it.attributes
        attributes.screenBrightness = brightness / 255f
        it.attributes = attributes
    }
}

fun Context.requestAudioFocus(call: AudioManager.OnAudioFocusChangeListener) {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.requestAudioFocus(
        call,
        AudioManager.STREAM_MUSIC,
        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
    )
}

fun Context.abandonAudioFocus(call: AudioManager.OnAudioFocusChangeListener) {
    val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.abandonAudioFocus(call)
}

fun Context.showActionBar() {
    getAppCompatActivity()?.supportActionBar?.let {
        it.setShowHideAnimationEnabled(false);
        it.show()
    }
    getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
}

fun Context.hideActionBar() {
    getAppCompatActivity()?.supportActionBar?.let {
        it.setShowHideAnimationEnabled(false);
        it.hide()
    }
    getWindow()?.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    );
}

val Context.screenWidth: Int get() = resources.displayMetrics.widthPixels

fun Context.registerApplicationLifecycle() {
    if (!registeredApplicationLifecycleCallbacks)
        (applicationContext as Application).registerActivityLifecycleCallbacks(
            applicationLifecycleCallbacks
        )
    registeredApplicationLifecycleCallbacks = true
}

fun Context.isLandscape(): Boolean {
    return resources.displayMetrics.widthPixels > resources.displayMetrics.heightPixels
}

fun Context.openFullIPlayer(
    url: Url,
    clazz: Class<out IPlayer> = DefaultPlayer::class.java,
    type: SurfaceType = SurfaceType.SCREEN_TYPE_CENTER_CROP
) {
    val contentView = getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT) ?: return
    contentView.findViewById<View>(R.id.player_zoom_full)
        ?.let { contentView.removeView(it) }
    hideActionBar()
    val constructor = clazz.getConstructor(Context::class.java)
    val currentPlayer = constructor.newInstance(this) as IPlayer
    currentPlayer.setDisplayType(type)
    currentPlayer.setMode(Mode.FULL)
    currentPlayer.id = R.id.player_zoom_full
    contentView.addView(currentPlayer, createFullScreenPlayerLayoutParams())
    currentPlayer.setDataSource(url)
}

fun Context.openWindowIPlayer(
    url: Url,
    clazz: Class<out IPlayer> = DefaultPlayer::class.java,
    size: Size = Size(screenWidth / 3, screenWidth / 3),
    type: SurfaceType = SurfaceType.SCREEN_TYPE_CENTER_CROP
) {
    val contentView = getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT) ?: return
    contentView.findViewById<View>(R.id.player_zoom_window)
        ?.let { contentView.removeView(it) }
    val constructor = clazz.getConstructor(Context::class.java)
    val currentPlayer = constructor.newInstance(this) as IPlayer
    currentPlayer.setDisplayType(type)
    currentPlayer.setMode(Mode.WINDOW)
    currentPlayer.id = R.id.player_zoom_window
    contentView.addView(currentPlayer, createWindowPlayerLayoutParams(size))
    currentPlayer.setDataSource(url)
}