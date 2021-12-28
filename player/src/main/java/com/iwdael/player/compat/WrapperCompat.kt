package com.iwdael.player.compat

import com.iwdael.player.Wrapper

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */

val <T> Wrapper<T>.value: T get() = this.get()