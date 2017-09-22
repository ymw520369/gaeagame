package com.gaea.game.dataserver.manager;

import com.alibaba.fastjson.JSON;
import com.gaea.game.core.data.PlatformConfig;
import com.gaea.game.core.data.PlatformUserInfo;
import com.gaea.game.core.data.VertifyInfo;
import org.alan.utils.HttpUtils;
import org.alan.utils.HttpUtils.HttpResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
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
        String source = vertifyInfo.uuid +"&"+ vertifyInfo.token + platformConfig.secretKey;
        try {
            //String sign = MD5Utils.encoderByMd5(source);

            String md5 = DigestUtils.md5Hex(source);
            Base64 base64 = new Base64();
            String encode = new String(base64.encode(md5.getBytes())).toLowerCase();
            param.put("uid", vertifyInfo.uuid);
            param.put("token", vertifyInfo.token);
            param.put("key", encode);
            HttpResponse httpResponse = HttpUtils.doPost(platformConfig.vertifyUrl, param);
            if (httpResponse.isOk()) {
                int code = httpResponse.getIntValue("code");
                if (code == 1) {
                    String data = httpResponse.getValueByKey("data");
                    PlatformUserInfo platformUserInfo = JSON.parseObject(data, PlatformUserInfo.class);
                    return platformUserInfo;
                } else {
                    String desc = httpResponse.getValueByKey("des");
                    log.warn("平台验证账号返回数据，code={},desc={}", code, desc);
                }
            }
        } catch (Exception e) {
            log.warn("向平台认证用户信息失败,vertifyInfo=" + JSON.toJSONString(vertifyInfo), e);
        }
        return null;
    }

}
