package com.gaea.game.logic.constant;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
public interface MessageConst {
    interface Login {
        int TYPE = 1000;
        int REQ_VERTIFY = 1;
        int RESP_VERTIFY = 2;
    }

    interface ROOM {
        int TYPE = 1001;
        int REQ_CREATE_ROOM = 1;
        int RESP_CREATE_ROOM = 2;
        int REQ_EXIT_ROOM = 3;
        int RESP_EXIT_ROOM = 4;
    }

    interface LHD {
        int TYPE = 1101;
        int REQ_BET = 1;
        int RESP_GAME_INFO = 2;
    }
}
