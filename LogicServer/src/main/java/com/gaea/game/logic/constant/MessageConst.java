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
        int TYPE = 1100;
        int REQ_CREATE_ROOM = 1101;
        int RESP_CREATE_ROOM = 1102;
        int REQ_EXIT_ROOM = 1103;
        int RESP_EXIT_ROOM = 1104;
    }

    interface LHD {
        int TYPE = 1200;
        int REQ_BET = 1201;
        int RESP_GAME_INFO = 1202;
    }
}
