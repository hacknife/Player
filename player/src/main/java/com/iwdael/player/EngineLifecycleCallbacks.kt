package com.iwdael.player

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.iwdael.player.callback.MediaCallback
import com.iwdael.player.compat.activityLifecycleCallbacks
import com.iwdael.player.compat.engineContainer
import com.iwdael.player.compat.getActivity

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */
class EngineLifecycleCallbacks(
    private val context: Context,
    private val callback: Wrapper<MediaCallback>,
    private val engine: Engine
) : Application.ActivityLifecycleCallbacks {
    private var enginePlayState = false
    private val activity = context.getActivity()!!
    private val pair = activity to this
    private var isSaveInstance = false
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        isSaveInstance = false
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        if (callback.get().state() < State.PLAYER_STATE_PREPARED) return
        if (enginePlayState) engine.start()
    }

    override fun onActivityPaused(activity: Activity) {
        if (callback.get().state() < State.PLAYER_STATE_PREPARED) return
        enginePlayState = engine.isPlaying()
        engine.pause()
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        isSaveInstance = true
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (!isSaveInstance) {
            engine.release()
            val remove = engineContainer.remove(engine.url.getCurrentUrl().toString())
        }

        activityLifecycleCallbacks.remove(pair)
    }

    fun register() {
        engineContainer[engine.url.getCurrentUrl().toString()] = engine
        activityLifecycleCallbacks.add(pair)
    }
}