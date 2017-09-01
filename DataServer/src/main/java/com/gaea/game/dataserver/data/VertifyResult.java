package com.gaea.game.dataserver.data;

import com.gaea.game.base.constant.ResultEnum;
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
    public String logicUrl;

    public VertifyResult(ResultEnum gameResultEnum, Credential credential, String logicUrl) {
        this.result = gameResultEnum.code;
        this.credential = credential;
        this.logicUrl = logicUrl;
    }
}
