package com.gaea.game.dataserver.controller;

import com.gaea.game.base.constant.GameResultEnum;
import com.gaea.game.base.data.Credential;
import com.gaea.game.base.data.VertifyInfo;
import com.gaea.game.base.manager.DataManager;
import com.gaea.game.dataserver.data.VertifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2017/8/24.
 *
 * @author Alan
 * @since 1.0
 */
@Controller
@RequestMapping("/vertify")
public class VertifyController {

    @Autowired
    DataManager dataManager;

    @RequestMapping("/credential")
    public VertifyResult vertify(VertifyInfo vertifyInfo) {
        VertifyResult vertifyResult;
        Credential credential = dataManager.vertifyAccount(vertifyInfo);
        if (credential != null) {
            vertifyResult = new VertifyResult(GameResultEnum.SUCCESS, credential);
        } else {
            vertifyResult = new VertifyResult(GameResultEnum.FAILURE, credential);
        }
        return vertifyResult;
    }

}
