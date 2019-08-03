package com.example.study.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.study.service.CommonService;
import com.example.study.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    private final static Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Override
    public String[] getOpenid(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        logger.debug(url);
        String openId = "";
        String session_key = "";
        try {
            HttpResponse response = HttpUtils.doGet(url, "", "GET", new HashMap<String, String>(), new HashMap<String, String>());
            logger.debug(response.toString());
            String body = EntityUtils.toString(response.getEntity());
            logger.debug("body" + body);
            JSONObject object = JSON.parseObject(body);
            openId = object.getString("openid");
            session_key = object.getString("session_key");
            logger.debug("openid" + object.getString("openid"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return new String[]{openId, session_key};
        }
    }
}
