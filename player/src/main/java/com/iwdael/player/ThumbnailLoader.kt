package com.iwdael.player

import android.widget.ImageView

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface ThumbnailLoader {
    fun load(view: ImageView, url: String)
}