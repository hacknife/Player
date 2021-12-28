package com.iwdael.player.compat

import android.util.Log
import com.iwdael.player.IPlayer
import java.util.ArrayList

/**
 * author : Iwdael
 * e-mail : iwdael@outlook.com
 * time   : 2019/8/5
 * desc   : MVVM
 * version: 1.0
 */


enum class Invoke(val method: String, vararg val args: Class<*>) {
    METHOD_SEEK("invokeSeek", Int::class.java),
    METHOD_SEEK_COMPLETE("invokeSeekComplete"),
    METHOD_PREPARING("invokePreparing"),
    METHOD_PREPARED("invokePrepared"),
    METHOD_PLAY_COMPLETION("invokePlayCompletion"),
    METHOD_BUFFER_UPDATE("invokeBufferingUpdate", Int::class.java),
    METHOD_PLAYING("invokePlaying"),
    METHOD_PAUSE("invokePause"),
    METHOD_ERROR("invokeError", Int::class.java, Int::class.java),
    METHOD_INFO("invokeInfo", Int::class.java, Int::class.java),
    METHOD_VIDEO_SIZE("invokeVideoSizeChanged", Int::class.java, Int::class.java)
}

fun IPlayer.invoke(invoke: Invoke, vararg args: Any?) {
    val method = if (invoke.args.isEmpty())
        IPlayer::class.java.getDeclaredMethod(invoke.method)
    else
        IPlayer::class.java.getDeclaredMethod(invoke.method, *invoke.args)
    method.isAccessible = true
    method.invoke(this, *args)
}