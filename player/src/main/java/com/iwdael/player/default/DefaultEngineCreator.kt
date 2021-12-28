package com.iwdael.player.default

import com.iwdael.player.*
import com.iwdael.player.callback.MediaCallback

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class DefaultEngineCreator : EngineCreator {
    override fun create(callback: MediaCallback, url: Url): Engine {
        return DefaultEngine(callback, url)
    }
}