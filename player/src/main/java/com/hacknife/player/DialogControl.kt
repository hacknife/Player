package com.hacknife.player

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.*
import com.hacknife.player.compat.delay
import kotlinx.android.synthetic.main.default_player_standard.*

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class DialogControl(private val context: Context) {
    private val dialogSet = mutableMapOf<Int, Pair<View, Dialog>>()
    fun showDialog(layoutRes: Int, autoClose: Long, call: (View) -> Unit) {
        val pair = dialogSet[layoutRes] ?: createDialog(layoutRes, autoClose)
        if (!pair.second.isShowing) pair.second.show()
        call.invoke(pair.first)
        dialogSet[layoutRes] = pair
    }

    fun showDialog(layoutRes: Int, call: (View) -> Unit) {
        showDialog(layoutRes, -1L, call)
    }

    fun isShowing(vararg ids: Int): Boolean {
        ids.forEach {
            if (dialogSet.containsKey(it))
                return true
        }
        return false
    }

    fun dismissDialog(layoutRes: Int) {
        dialogSet.remove(layoutRes)?.let {
            it.second.dismiss()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createDialog(id: Int, autoClose: Long): Pair<View, Dialog> {
        val view = LayoutInflater.from(context).inflate(id, null)
        val dialog = CommonDialog(context, R.style.player_dialog_common)
        dialog.setContentView(view)
        val window = dialog.window
        window!!.addFlags(Window.FEATURE_ACTION_BAR)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
        if (autoClose == -1L)
            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        window.setLayout(-2, -2)
        val localLayoutParams = window.attributes
        localLayoutParams.gravity = Gravity.CENTER
        window.attributes = localLayoutParams
        if (autoClose != -1L) {
            view.setOnTouchListener { _, e ->
                if (e.action == MotionEvent.ACTION_UP || e.action == MotionEvent.ACTION_DOWN)
                    view.delay(autoClose) {
                        dismissDialog(id)
                    }
                return@setOnTouchListener false
            }
            view.delay(autoClose) {
                dismissDialog(id)
            }
        }
        return view to dialog
    }


    class CommonDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
        private var touchListener: (() -> Unit)? = null

        fun setOnTouchListener(touchListener: (() -> Unit)) {
            this.touchListener = touchListener
        }

        override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
            touchListener?.invoke()
            super.dispatchTouchEvent(ev)
            return true
        }

    }

}