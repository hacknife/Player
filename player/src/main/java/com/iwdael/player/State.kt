package com.iwdael.player

/**
 * author  : iwdael
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/iwdael
 * project : IPlayer //状态
 */
enum class State(val value: Int) {

    PLAYER_STATE_RELEASE(-1),
    PLAYER_STATE_ERROR(0),
    PLAYER_STATE_NORMAL(1),  //默认状态
    PLAYER_STATE_PREPARING(2),  //准备中
    PLAYER_STATE_PREPARING_CHANGING_URL(2),  //切换播放源后准备中
    PLAYER_STATE_PREPARING_SEEKING(2),  //切换播放源后准备中
    PLAYER_STATE_PREPARED(3),  //准备中

    PLAYER_STATE_PLAYING(4),  //播放中
    PLAYER_STATE_PAUSE(5),  //暂停
    PLAYER_STATE_PLAY_COMPLETE(-2)   //播放完
    //播放错误
}