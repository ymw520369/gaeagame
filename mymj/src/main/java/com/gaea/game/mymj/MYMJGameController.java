package com.gaea.game.mymj;

import com.gaea.game.logic.data.GameInfo;
import com.gaea.game.logic.data.PlayerController;
import com.gaea.game.logic.game.GameController;

/**
 * Created on 2017/9/30.
 *
 * @author Alan
 * @since 1.0
 */
public class MYMJGameController extends GameController<MjConfigInfo> {
    /* 当前是第几局*/
    protected int round = 0;
    /* 当前状态*/
    protected MJStatus currentStatus = MJStatus.INIT;

    protected long[] players;

    public void start() {

    }

    public void onMessage(PlayerController playerController, Object msg) {

    }

    public GameInfo getGameInfo() {
        return null;
    }
}
