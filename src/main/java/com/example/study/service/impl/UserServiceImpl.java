package com.example.study.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.study.controller.jersey.WechatController;
import com.example.study.mapper.UserMapper;
import com.example.study.service.IMService;
import com.example.study.service.UserService;

import com.example.study.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import com.example.study.object.User;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IMService imService;

    @Override
    public String userLogin(String openId) {
        if(userMapper.selectByPrimaryKey(openId) == null){
            User user = new User();
            user.setOpenId(openId);
            user.setFirstLogin(new Date());
            user.setLastLogin(new Date());
            userMapper.insert(user);
            imUserRegister(openId);
            logger.info("新用户登录！");
        }else{
            User user = userMapper.selectByPrimaryKey(openId);
            user.setLastLogin(new Date());
            userMapper.updateByPrimaryKey(user);
            logger.info("老用户登录！");
            return user.getTaskId();
        }
        return null;
    }

    @Override
    public int imUserRegister(String openId) {
        final Base64.Encoder encoder = Base64.getEncoder();
        String account = encoder.encodeToString(openId.getBytes()).replace("=","");
        logger.debug("IM account:" + account);
        return imService.userRegister(account, account);
    }

    @Override
    public String releaseUser(String taskId) {
        User user = userMapper.selectByTaskId(taskId);
        if(user != null){
            user.setTaskId(null);
            userMapper.updateByPrimaryKey(user);
        }
        return null;
    }
}

