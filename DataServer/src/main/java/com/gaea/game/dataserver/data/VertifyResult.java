package com.gaea.game.dataserver.data;

import com.gaea.game.base.constant.GameResultEnum;
import com.gaea.game.base.data.Credential;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
public class VertifyResult {
    public int result;
    public Credential credential;

    public VertifyResult(GameResultEnum gameResultEnum, Credential credential) {
        this.result = gameResultEnum.code;
        this.credential = credential;
    }
}
