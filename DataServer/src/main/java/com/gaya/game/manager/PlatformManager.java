package com.gaya.game.manager;

import com.alibaba.fastjson.JSON;
import com.gaya.game.data.PlatformConfig;
import com.gaya.game.data.PlatformUserInfo;
import com.gaya.game.data.VertifyInfo;
import org.alan.utils.HttpUtils;
import org.alan.utils.HttpUtils.HttpResponse;
import org.alan.utils.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 平台管理器
 * Created on 2017/8/23.
 *
 * @author Alan
 * @since 1.0
 */
@Component
public class PlatformManager {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    PlatformConfig platformConfig;

    /**
     * 向平台认证用户信息
     *
     * @param vertifyInfo
     * @return
     */
    public PlatformUserInfo vertify(VertifyInfo vertifyInfo) {
        Map<String, String> param = new HashMap<>();
        String source = vertifyInfo.uuid + "&" + vertifyInfo.token + "&" + platformConfig.secretKey;
        try {
            String sign = MD5Utils.encoderByMd5(source);
            param.put("uuid", vertifyInfo.uuid);
            param.put("token", vertifyInfo.token);
            param.put("sign", sign);
            HttpResponse httpResponse = HttpUtils.doPost(platformConfig.vertifyUrl, param);
            if (httpResponse.isOk()) {
                int code = httpResponse.getIntValue("code");
                if (code == 0) {
                    String data = httpResponse.getValueByKey("data");
                    PlatformUserInfo platformUserInfo = JSON.parseObject(data, PlatformUserInfo.class);
                    return platformUserInfo;
                }
            }
        } catch (Exception e) {
            log.warn("向平台认证用户信息失败,vertifyInfo=" + JSON.toJSONString(vertifyInfo), e);
        }
        return null;
    }

}
