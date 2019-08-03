package com.example.study.service;

import java.util.List;
import java.util.Map;

public interface RescuerService {

    /**
     * 获取救援人员状态
     * @param openId 救援人员openId
     */
    String[] getStatus(String openId);

    /**
     * 获取救援人员当前任务
     * @param openId 救援人员openId
     */
    String getTaskId(String openId);

    /**
     * 获取救援人员当前任务
     * @param openId 救援人员openId
     */
    String setTaskId(String openId, String taskId);

    /**
     * 救援人员注册
     * @param unitId 救援单位编号
     * @param unitKey 救援单位密钥
     * @param name 救援人员名称
     * @param id 救援人员工号
     * @param gender 救援人员性别
     * @param phone 救援人员手机号
     * @param openId 救援人员openId
     */
    int register(String unitId, String unitKey, String name, String id, String gender, String phone, String openId);

    /**
     * 修改救援人员在线状态
     * @param openId 救援人员openId
     * @param status 待修改的救援人员状态
     */
    int changeStatus(String openId, String status);

    /**
     * 任务结束后释放所有救援人员状态
     * @param taskId 任务Id
     */
    String releaseRescuer(String taskId);

    List getFreeRescuer();
}
