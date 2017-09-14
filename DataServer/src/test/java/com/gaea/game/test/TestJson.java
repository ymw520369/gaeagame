package com.gaea.game.test;

import com.alibaba.fastjson.JSON;
import com.gaea.game.core.data.VertifyInfo;

/**
 * Created on 2017/9/13.
 *
 * @author Alan
 * @since 1.0
 */
public class TestJson {

    public static void main(String[] args) {
        String json = "{\"uuid\": \"101\",\"token\":\"logic 3\"}";
        VertifyInfo vertifyInfo = JSON.parseObject(json, VertifyInfo.class);

        System.out.println(vertifyInfo.token);
    }
}
