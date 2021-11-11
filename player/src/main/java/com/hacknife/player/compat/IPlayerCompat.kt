package com.hacknife.player.compat

import android.graphics.SurfaceTexture
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.FrameLayout
import com.hacknife.player.Engine
import com.hacknife.player.IPlayer
import com.hacknife.player.R
import com.hacknife.player.State
import com.hacknife.player.widget.SurfaceView

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */


fun IPlayer.bindSurface(engine: Engine, texture: SurfaceView) {
    val playerSurface = findViewById<FrameLayout>(R.id.playerSurface)
    playerSurface.removeAllViews()
    texture.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(st: SurfaceTexture, i: Int, i2: Int) {
            if (engine.callback.get().state() < State.PLAYER_STATE_PREPARING) {
                engine.preloading()
                engine.prepare()
            }
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