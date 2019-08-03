package com.example.study.controller.jersey;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.study.service.impl.CommonServiceImpl;
import com.example.study.service.impl.RescuerServiceImpl;
import com.example.study.service.impl.TaskServiceImpl;
import com.example.study.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Created by tigris on 2019/5/6.
 */
@Component
@Path("/api")
public class WechatController {

    private final static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @Autowired
    CommonServiceImpl commonService;

    @Autowired
    RescuerServiceImpl rescuerService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TaskServiceImpl taskService;

    /**
     * 小程序初始化时请求的信息
     * @param code 用于换取用户openId的神秘代码
     */
    @GET
    @Path("/initMsg")
    @Produces(MediaType.APPLICATION_JSON)
    public String getInitMsg(@QueryParam("code") String code){
        String[] wechatInfo = commonService.getOpenid(code);
        String openId = wechatInfo[0];
        String session_key = wechatInfo[1];
        String taskId = userService.userLogin(openId);
        String[] rescuerInfo = rescuerService.getStatus(openId);
        String rescuerId = null;
        String status = null;
        logger.debug("用户状态：" + status);
        if(rescuerInfo == null){
            status = "普通用户";
        }else if(rescuerInfo[1].equals("在线")){
            rescuerId = rescuerInfo[0];
            status = "救援人员（在线）";
            taskId = rescuerService.getTaskId(openId);
        }else{
            rescuerId = rescuerInfo[0];
            status = "救援人员（离线）";
            taskId = rescuerService.getTaskId(openId);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openId", openId);
        jsonObject.put("identity", status);
        jsonObject.put("rescuerId", rescuerId);
        jsonObject.put("taskId", taskId);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 建立救援任务
     * @param eventLocationName 事件发生地名称
     * @param eventLocationCoordinate 事件发生地坐标
     * @param aidLocationName 求救单位名称
     * @param aidLocationCoordinate 求救单位坐标
     * @param aidType 求救类型
     * @param openId 用户openId
     */
    @POST
    @Path("/task")
    @Produces(MediaType.APPLICATION_JSON)
    public String createTask(@FormParam("eventLocationName") String eventLocationName,
                             @FormParam("eventLocationCoordinate") String eventLocationCoordinate,
                             @FormParam("aidLocationName") String aidLocationName,
                             @FormParam("aidLocationCoordinate") String aidLocationCoordinate,
                             @FormParam("aidType") String aidType,
                             @FormParam("openId") String openId){
        String taskId = taskService.createTask(eventLocationName, eventLocationCoordinate, aidLocationName, aidLocationCoordinate, aidType, openId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", taskId);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 小程序端获取任务详细信息
     * @param taskId 任务id
     * @return
     */
    @GET
    @Path("/task/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTaskInfo(@PathParam("taskId") String taskId){
        Map taskInfo = taskService.getTaskInfo(taskId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", taskInfo.get("status"));
        jsonObject.put("rescuerName", taskInfo.get("rescuerName"));
        jsonObject.put("rescuerPhone", taskInfo.get("rescuerPhone"));
        jsonObject.put("eventLocationName", taskInfo.get("eventLocationName"));
        jsonObject.put("eventLocationCoordinate", taskInfo.get("eventLocationCoordinate"));
        jsonObject.put("aidLocationName", taskInfo.get("aidLocationName"));
        jsonObject.put("aidLocationCoordinate", taskInfo.get("aidLocationCoordinate"));
        jsonObject.put("type", taskInfo.get("type"));
        jsonObject.put("aidRoute", JSONArray.parseArray((String)taskInfo.get("aidRoute")));
        jsonObject.put("chatGroupId", taskInfo.get("chatGroupId"));
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 救援人员更新实时坐标
     * @param taskId 任务id
     * @param rescuerLocation 救援人员坐标，以逗号分隔
     * @param openId 救援人员openId
     */
    @PUT
    @Path("/rescuerLocation/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateRescuerLocation(@PathParam("taskId") String taskId,
                                        @FormParam("rescuerLocation") String rescuerLocation,
                                        @FormParam("openId") String openId){
        taskService.setRescuerLocation(taskId, rescuerLocation, openId);
        return null;
    }

    /**
     * 小程序端获取救援人员实时坐标
     * @param taskId 任务id
     */
    @GET
    @Path("/rescuerLocation/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRescuerLocation(@PathParam("taskId") String taskId){
        String rescuerLocation = taskService.getRescuerLocation(taskId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rescuerLocationCoordinate", rescuerLocation);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

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
    @POST
    @Path("/rescuer")
    @Produces(MediaType.APPLICATION_JSON)
    public String createRescuer(@FormParam("unitId") String unitId,
                                @FormParam("unitKey") String unitKey,
                                @FormParam("name") String name,
                                @FormParam("id") String id,
                                @FormParam("gender") String gender,
                                @FormParam("phone") String phone,
                                @FormParam("openId") String openId){
        int status = rescuerService.register(unitId, unitKey, name, id, gender, phone, openId);
        String info;
        switch (status){
            case -1:info = "单位信息错误！请与管理员联系！"; break;
            case 0:info = "该用户已注册！请与管理员联系！"; break;
            case 1:info = "注册成功！"; break;
            default:info = "未知错误！请与管理员联系！";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("info", info);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 救援人员上下线
     * @param rescuerId 救援人员id
     * @param status 救援人员状态
     * @param openId 救援人员openId
     */
    @PUT
    @Path("/rescuer/status/{rescuerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateRescuerStatus(@PathParam("rescuerId") String rescuerId,
                                      @FormParam("status") String status,
                                      @FormParam("openId") String openId){
        int nowStatus = rescuerService.changeStatus(openId, status);
        String info = "操作异常！";
        if (nowStatus == 1){
            info = "操作成功！";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("info", info);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 小程序救援人员定时取得救援任务
     * @param openId 救援人员openId
     */
    @GET
    @Path("/rescuer/task")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRescuerTask(@QueryParam("openId") String openId){
        String taskId = rescuerService.getTaskId(openId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskId", taskId);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }
}
