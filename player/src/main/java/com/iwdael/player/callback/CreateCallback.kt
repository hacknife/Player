package com.iwdael.player.callback

import android.content.Context
import android.graphics.Rect
import android.util.Size
import com.iwdael.player.IPlayer
import com.iwdael.player.Mode
import com.iwdael.player.State

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
interface CreateCallback {
    fun context(): Context
    fun createPlayer(): Pair<IPlayer, CreateCallback>
    fun switchPlayer(player: IPlayer)
    fun getCurrentPlayer(): IPlayer
    fun getCurrentVideoSize(): Size
    fun getPreviousPlayer(): Pair<IPlayer, CreateCallback>?
    fun setMode(mode: Mode)
    fun getMode(): Mode
    fun setState(state: State)
    fun getState(): State
    fun isUsed():Boolean
}