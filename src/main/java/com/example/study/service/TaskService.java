package com.example.study.service;

import java.util.Map;

public interface TaskService {

    /**
     * 创建救援任务
     * @param eventLocationName 事件发生地名称
     * @param eventLocationCoordinate 事件发生地坐标
     * @param aidLocationName 求救单位名称
     * @param aidLocationCoordinate 求救单位坐标
     * @param aidType 求救类型
     * @param openId 用户openId
     */
    String createTask(String eventLocationName,String eventLocationCoordinate, String aidLocationName, String aidLocationCoordinate, String aidType, String openId);

    /**
     * 获取救援任务详情
     * @param taskId 救援任务Id
     */
    Map getTaskInfo(String taskId);

    /**
     * 救援任务设置救援队长
     * @param taskId 任务Id
     * @param openId 救援队长openId
     */
    void taskAddRescuer(String taskId, String openId);

    /**+
     * 为救援任务创建通信群组
     * @param groupName 群组名，为任务Id
     * @param desc 群组描述，为任务Id
     * @param openId 群主openId， 一般为求救者
     * @return
     */
    String imGroupCreate(String groupName, String desc, String openId);

    /**
     * 救援任务通信群组添加人员
     * @param taskId 任务id
     * @param openId 待添加人员的openId
     * @return
     */
    String imGroupAddRescuer(String taskId, String openId);


    int setReceiver(String taskId, String receiverId);

    /**
     * 更新救援人员位置
     * @param taskId 任务Id
     * @param Coordinate 救援人员坐标
     * @param openId 救援人员openId
     */
    void setRescuerLocation(String taskId, String Coordinate, String openId);

    /**
     * 获取救援人员位置
     * @param taskId 任务Id
     */
    String getRescuerLocation(String taskId);

    /**
     * 获取救援路线规划
     * @param taskId 任务Id
     * @param eventLocationCoordinate 求救位置坐标
     */
    String getAidRoute(String taskId, String eventLocationCoordinate);

    /**
     * 结束救援任务
     * @param taskId 任务Id
     */
    String endTask(String taskId);
}
