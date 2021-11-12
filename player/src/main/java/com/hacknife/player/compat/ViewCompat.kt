package com.hacknife.player.compat

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import com.hacknife.player.IPlayer
import com.hacknife.player.R
import com.hacknife.player.touch.SeekDurationListener

/**
 * author : 段泽全(hacknife)
 * e-mail : hacknife@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */

fun View.setText(id: Int, text: String) {
    findViewById<TextView>(id)?.text = text
}

fun View.setDrawable(id: Int, resource: Int) {
    findViewById<ImageView>(id)?.setImageResource(resource)
}

fun View.setVisibility(id: Int, value: Boolean) {
    findViewById<View>(id)?.visibility = if (value) View.VISIBLE else View.GONE
}

fun View.setSeek(id: Int, progress: Int) {
    findViewById<SeekBar>(id)?.progress = progress
}

fun View.setSeekMax(id: Int, progress: Int) {
    findViewById<SeekBar>(id)?.max = progress
}

fun View.setProgress(id: Int, progress: Int) {
    findViewById<ProgressBar>(id)?.progress = progress
}

fun View.setProgressMax(id: Int, progress: Int) {
    findViewById<ProgressBar>(id)?.max = progress
}

fun View.setSeekSecond(id: Int, progress: Int) {
    findViewById<SeekBar>(id)?.secondaryProgress = progress
}

fun View.performClick(id: Int) {
    findViewById<View>(id)?.performClick()
}

fun IPlayer.bindClick(id: Int, listener: View.OnClickListener) {
    findViewById<View>(id)?.setOnClickListener { listener.onClick(it) }
}


fun IPlayer.bindTouch(id: Int, listener: View.OnTouchListener) {
    findViewById<View>(id)?.setOnTouchListener(listener)
}

fun View.bindSeekBar(id: Int, call: SeekDurationListener) {
    findViewById<SeekBar>(id)?.setOnSeekBarChangeListener(call)
}

fun View.setSelected(id: Int, value: Boolean) {
    findViewById<View>(id)?.isSelected = value
}

fun View.setClick(id: Int, call: () -> Unit) {
    findViewById<View>(id)?.setOnClickListener { call.invoke() }
}

fun View.setTouch(id: Int, call: () -> Unit) {
    findViewById<View>(id)?.setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN)
            call.invoke()
        false
    }
}

fun View.delay(time: Long, call: (() -> Unit)) {
    this.setTag(R.id.view_delay, System.currentTimeMillis() + time)
    this.postDelayed({
        val setTime = this.getTag(R.id.view_delay) as Long
        if (setTime < System.currentTimeMillis())
            call.invoke()
    }, time + 200)
}