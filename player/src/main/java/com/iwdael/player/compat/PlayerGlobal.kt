package com.iwdael.player.compat

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Size
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.iwdael.player.Engine

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */

internal val mainHandler by lazy { Handler(Looper.getMainLooper()) }
internal fun runMain(call: () -> Unit) = mainHandler.post { call.invoke() }
internal val activityLifecycleCallbacks =
    ArrayList<Pair<Activity, Application.ActivityLifecycleCallbacks>>()
internal val engineContainer = hashMapOf<String, Engine>()
internal var registeredApplicationLifecycleCallbacks = false
internal val applicationLifecycleCallbacks = object :
    Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val copy = activityLifecycleCallbacks.toList()
        copy.forEach {
            if (it.first == activity) it.second.onActivityCreated(activity, savedInstanceState)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        val copy = activityLifecycleCallbacks.toList()
        copy.forEach {
            if (it.first == activity) it.second.onActivityStarted(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        val copy = activityLifecycleCallbacks.toList()
        copy.forEach {
            if (it.first == activity) it.second.onActivityResumed(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        val copy = activityLifecycleCallbacks.toList()
        copy.forEach {
            if (it.first == activity) it.second.onActivityPaused(activity)
        }
    }

    override fun onActivityStopped(activity: Activity) {
        val copy = activityLifecycleCallbacks.toList()
        copy.forEach {
            if (it.first == activity) it.second.onActivityStopped(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        val copy = activityLifecycleCallbacks.toList()
        copy.forEach {
            if (it.first == activity) it.second.onActivitySaveInstanceState(activity, outState)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        val copy = activityLifecycleCallbacks.toList()
        copy.forEach {
            if (it.first == activity) it.second.onActivityDestroyed(activity)
        }
    }
}

fun createFullScreenPlayerLayoutParams() = FrameLayout.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.MATCH_PARENT
)

fun createSurfaceViewLayoutParams() = FrameLayout.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.MATCH_PARENT,
    Gravity.CENTER
)

fun Context.createWindowPlayerLayoutParams(size: Size): FrameLayout.LayoutParams {
    val scale = (size.height * 1f) / size.width
    val targetWidth = (screenWidth * 0.35f).toInt()
    val targetHeight = (targetWidth * scale).toInt()
    return FrameLayout.LayoutParams(targetWidth, targetHeight)
        .apply {
            this.gravity = Gravity.RIGHT or Gravity.TOP
        }
}