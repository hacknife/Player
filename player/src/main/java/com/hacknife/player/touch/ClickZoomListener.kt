package com.hacknife.player.touch

import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.hacknife.player.IPlayer
import com.hacknife.player.Mode
import com.hacknife.player.R
import com.hacknife.player.State
import com.hacknife.player.callback.CreateCallback
import com.hacknife.player.compat.*

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class ClickZoomListener(private val previousPlayerCallback: CreateCallback) : ClickPlayerListener {
    override fun clear() {

    }

    override fun onClick(v: View?) {
        if (!previousPlayerCallback.isUsed()) return
        if (previousPlayerCallback.getMode() == Mode.NORMAL) {
            val context = previousPlayerCallback.context()
            val contentView =
                context.getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT) ?: return
            contentView.findViewById<View>(R.id.player_zoom_full)
                ?.let {
                    it as IPlayer
                    contentView.removeView(it)
                    it.release()
                }
            context.hideActionBar()
            context.keepScreenOn()
            val pair = previousPlayerCallback.createPlayer()
            val currentPlayer = pair.first
            currentPlayer.id = R.id.player_zoom_full
            val currentPlayerCallback = pair.second
            currentPlayerCallback.setMode(Mode.FULL)
            currentPlayerCallback.switchPlayer(previousPlayerCallback.getCurrentPlayer())
            contentView.addView(currentPlayer, createFullScreenPlayerLayoutParams())
            previousPlayerCallback.setState(State.PLAYER_STATE_NORMAL)
        } else if (previousPlayerCallback.getMode() == Mode.FULL) {
            val context = previousPlayerCallback.context()
            val contentView =
                context.getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT) ?: return
            contentView.findViewById<View>(R.id.player_zoom_full)
                ?.let { contentView.removeView(it) }
            context.showActionBar()
            context.clearScreenOn()
            val pair = previousPlayerCallback.getPreviousPlayer()
            if (pair == null) {
                previousPlayerCallback.getCurrentPlayer().release()
                context.getActivity()?.onBackPressed()
            } else {
                val currentPlayerCallback = pair.second
                currentPlayerCallback.switchPlayer(previousPlayerCallback.getCurrentPlayer())
            }
        }
    }
}