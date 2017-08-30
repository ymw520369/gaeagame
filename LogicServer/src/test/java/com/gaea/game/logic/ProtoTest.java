package com.gaea.game.logic;

import com.gaea.game.base.data.Role;
import com.gaea.game.base.protobuf.ProtostuffUtil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created on 2017/8/28.
 *
 * @author Alan
 * @since 1.0
 */
public class ProtoTest {
    public static void main(String[] args) {
        Role role = new Role();
        role.userId = "id1";
        role.money = new AtomicLong(10);
        role.name = "asda";
        role.roleUid = 10;
        byte[] data = ProtostuffUtil.serialize(role);
        Role role2 = ProtostuffUtil.deserialize(data, Role.class);
        System.out.println(role2.money);
    }
}
