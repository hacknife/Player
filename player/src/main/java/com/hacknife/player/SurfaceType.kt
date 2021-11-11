package com.hacknife.player

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
enum class SurfaceType(val value: Int) {
    SCREEN_TYPE_NORMAL(0),
    SCREEN_TYPE_FIT_XY(1),
    SCREEN_TYPE_ORIGINAL(2),
    SCREEN_TYPE_CENTER_CROP(3)
}