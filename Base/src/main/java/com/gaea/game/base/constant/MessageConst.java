package com.gaea.game.base.constant;

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
    }
}
