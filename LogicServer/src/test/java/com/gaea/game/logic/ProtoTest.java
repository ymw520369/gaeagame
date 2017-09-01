package com.gaea.game.logic;

import com.gaea.game.base.data.Credential;
import com.gaea.game.base.protobuf.ProtostuffUtil;
import com.gaea.game.base.ws.GameMessage;

import java.util.Arrays;

/**
 * Created on 2017/8/28.
 *
 * @author Alan
 * @since 1.0
 */
public class ProtoTest {
    public static void main(String[] args) {
        Credential credential = new Credential("4848f958-0b85-43cf-86d7-3ee07cc0873b", 10043);
        credential.sign = null;
        byte[] d1 = ProtostuffUtil.serialize(credential);
        GameMessage gameMessage = new GameMessage(1000, 1, d1);
        byte[] data = ProtostuffUtil.serialize(gameMessage);
        System.out.println("len=" + data.length);
        System.out.println(Arrays.toString(data));
    }
}
