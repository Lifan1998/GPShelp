package com.example.study.service;

public interface CommonService {

    /**
     * 用code换取openId
     * @param code 微信提供的神秘代码
     */
    String[] getOpenid(String code);
}
