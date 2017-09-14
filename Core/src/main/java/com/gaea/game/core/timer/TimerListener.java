/*
 * Copyright (c) 2017. Chengdu Qianxing Technology Co.,LTD.
 * All Rights Reserved.
 */

package com.gaea.game.core.timer;

/**
 * 定时事件监听器
 *
 * Created on 2017/3/15.
 *
 * @author Alan
 * @since 1.0
 */
public interface TimerListener {
    /**
     * 定时事件的监听方法
     */
    void onTimer(TimerEvent e);
}
