package com.hacknife.player

import android.widget.ImageView

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface ThumbnailLoader {
    fun load(view: ImageView, url: String)
}