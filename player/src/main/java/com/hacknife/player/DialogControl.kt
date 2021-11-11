package com.hacknife.player

import android.app.Dialog
import android.content.Context
import android.view.*

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class DialogControl(private val context: Context) {
    private val dialogSet = mutableMapOf<Int, Pair<View, Dialog>>()
    fun showDialog(layoutRes: Int, call: (View) -> Unit) {
        val pair = dialogSet[layoutRes] ?: createDialog(layoutRes)
        if (!pair.second.isShowing) pair.second.show()
        call.invoke(pair.first)
        dialogSet[layoutRes] = pair
    }

    fun dismissDialog(layoutRes: Int) {
        dialogSet.remove(layoutRes)?.let {
            it.second.dismiss()
        }
    }

    private fun createDialog(id: Int): Pair<View, Dialog> {
        val view = LayoutInflater.from(context).inflate(id, null)
        val dialog = Dialog(context, R.style.player_dialog_common)
        dialog.setContentView(view)
        val window = dialog.window
        window!!.addFlags(Window.FEATURE_ACTION_BAR)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        window.setLayout(-2, -2)
        val localLayoutParams = window.attributes
        localLayoutParams.gravity = Gravity.CENTER
        window.attributes = localLayoutParams
        return view to dialog
    }

}