package com.hacknife.player.compat

import com.hacknife.player.Wrapper

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */

val <T> Wrapper<T>.value: T get() = this.get()