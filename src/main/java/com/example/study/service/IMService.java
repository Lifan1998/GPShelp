package com.example.study.service;


public interface IMService {

    /**
     * 环信IM获取操作令牌
     */
    String getToken();

    /**
     * 注册IM 账号
     * @param account 账号
     * @param password 密码
     */
    int userRegister(String account, String password);

    /**
     * 创建群组
     * @param groupName 群组名
     * @param desc 群组描述
     * @param owner 群主
     */
    String creatGroup(String groupName, String desc, String owner);

    /**
     * 群组添加用户
     * @param groupId 群组Id
     * @param userName 待添加的用户名
     */
    int groupAddUser(String groupId, String userName);

    /**
     * 删除群组
     * @param groupId 群组Id
     */
    int deleteGroup(String groupId);
}
