package com.example.study.service;


public interface UserService {

    /**
     * 用户登录
     * 新用户注册IM并存入数据库，老用户更新登陆时间
     * @param openId 用户openId
     */
    String userLogin(String openId);

    /**
     * 新用户注册IM
     * @param openId 用户openId
     */
    int imUserRegister(String openId);

    /**
     * 任务结束后释放用户状态
     * @param taskId 任务Id
     */
    String releaseUser(String taskId);
}
