package com.iwdael.player

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class Wrapper<T>(e1: T? = null, private val change: ((T?, T) -> Unit)? = null) {
    private var entity: T? = e1

    fun set(entity: T) {
        val old = this.entity
        this.entity = entity
        change?.invoke(old, entity)
    }

    fun get() = entity!!

    fun getOrNull() = entity

    fun isNull() = entity == null
}