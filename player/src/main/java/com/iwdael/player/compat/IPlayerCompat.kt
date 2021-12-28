package com.iwdael.player.compat

import android.graphics.SurfaceTexture
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.FrameLayout
import com.iwdael.player.Engine
import com.iwdael.player.IPlayer
import com.iwdael.player.R
import com.iwdael.player.State
import com.iwdael.player.widget.SurfaceView

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */


fun IPlayer.bindSurface(engine: Engine, texture: SurfaceView) {
    val playerSurface = findViewById<FrameLayout>(R.id.playerSurface)
    playerSurface.removeAllViews()
    texture.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(st: SurfaceTexture, i: Int, i2: Int) {
            if (engine.callback.get().state() < State.PLAYER_STATE_PREPARING) engine.prepare()
            engine.setSurface(Surface(st))
        }

        override fun onSurfaceTextureSizeChanged(st: SurfaceTexture, i: Int, i2: Int) {}
        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture) = false
        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
            texture.isTextureUpdated = true
        }
    }
    playerSurface.addView(texture, createSurfaceViewLayoutParams())
}

fun IPlayer.unBindSurface() {
    val playerSurface = findViewById<FrameLayout>(R.id.playerSurface)
    playerSurface.removeAllViews()
}