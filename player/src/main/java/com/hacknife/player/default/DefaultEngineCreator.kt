package com.hacknife.player.default

import com.hacknife.player.*
import com.hacknife.player.callback.MediaCallback

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class DefaultEngineCreator : EngineCreator {
    override fun create(callback: MediaCallback, url: Url): Engine {
        return DefaultEngine(callback, url)
    }
}