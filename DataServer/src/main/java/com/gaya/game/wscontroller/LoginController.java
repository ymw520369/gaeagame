package com.gaya.game.wscontroller;

import com.gaya.game.constant.MessageConst;
import com.gaya.game.data.VertifyInfo;
import com.gaya.game.manager.DataManager;
import com.gaya.game.ws.Command;
import com.gaya.game.ws.GameSession;
import com.gaya.game.ws.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
@Component
@MessageType(MessageConst.Login.TYPE)
public class LoginController {

    @Autowired
    DataManager dataManager;

    @Command(MessageConst.Login.REQ_VERTIFY)
    public void login(GameSession session, VertifyInfo vertifyInfo) {
        dataManager.vertifyAccount(session, vertifyInfo);
    }
}
