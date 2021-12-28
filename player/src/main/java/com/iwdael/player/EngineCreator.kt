package com.iwdael.player

import com.iwdael.player.callback.MediaCallback


/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface EngineCreator {
    fun create(callback: MediaCallback, url: Url): Engine
}