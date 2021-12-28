package com.iwdael.player.touch

import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.iwdael.player.IPlayer
import com.iwdael.player.Mode
import com.iwdael.player.R
import com.iwdael.player.State
import com.iwdael.player.callback.CreateCallback
import com.iwdael.player.compat.*

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class ClickWindowListener(private val previousPlayerCallback: CreateCallback) :
    ClickPlayerListener {
    override fun clear() {

    }

    override fun onClick(v: View) {
        if (!previousPlayerCallback.isUsed()) return
        if (previousPlayerCallback.getMode() == Mode.NORMAL && previousPlayerCallback.getState() > State.PLAYER_STATE_PREPARED) {
            val context = previousPlayerCallback.context()
            val contentView =
                context.getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT) ?: return
            contentView.findViewById<View>(R.id.player_zoom_window)
                ?.let {
                    it as IPlayer
                    contentView.removeView(it)
                    it.release()
                }
            val pair = previousPlayerCallback.createPlayer()
            val currentPlayer = pair.first
            currentPlayer.id = R.id.player_zoom_window
            val currentPlayerCallback = pair.second
            currentPlayerCallback.setMode(Mode.WINDOW)
            currentPlayerCallback.switchPlayer(previousPlayerCallback.getCurrentPlayer())
            contentView.addView(
                currentPlayer,
                context.createWindowPlayerLayoutParams(previousPlayerCallback.getCurrentVideoSize())
            )
            previousPlayerCallback.setState(State.PLAYER_STATE_NORMAL)
        } else if (previousPlayerCallback.getMode() == Mode.WINDOW) {
            val context = previousPlayerCallback.context()
            val contentView =
                context.getActivity()?.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT) ?: return
            contentView.findViewById<View>(R.id.player_zoom_window)
                ?.let { contentView.removeView(it) }
            val pair = previousPlayerCallback.getPreviousPlayer()
            if (pair == null) {
                previousPlayerCallback.getCurrentPlayer().release()
            } else {
                val currentPlayerCallback = pair.second
                currentPlayerCallback.switchPlayer(previousPlayerCallback.getCurrentPlayer())
            }
        }
    }
}