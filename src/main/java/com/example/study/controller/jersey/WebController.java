package com.example.study.controller.jersey;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.study.service.impl.ReceiverServiceImpl;
import com.example.study.service.impl.RescuerServiceImpl;
import com.example.study.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Created by tigris on 2019/5/15.
 */
@Component
@Path("/api/web")
public class WebController {


    @Autowired
    TaskServiceImpl taskService;

    @Autowired
    ReceiverServiceImpl receiverService;

    @Autowired
    RescuerServiceImpl rescuerService;

    @POST
    @Path("/{type}/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String receiverLogin(@PathParam("type") String type,
                                @FormParam("account") String account,
                                @FormParam("password") String password) {
        Map result = receiverService.receiverLogin(account, password);
        JSONObject jsonObject = new JSONObject(result);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    @GET
    @Path("/{type}/receiver/{receiverId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getReceiverInfo(@PathParam("type") String type,
                                @PathParam("receiverId") String receiverId) {
        Map result = receiverService.receiverInfo(receiverId);
        JSONObject jsonObject = new JSONObject(result);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    @GET
    @Path("/{type}/receiver/{receiverId}/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    public String getReceiverTasks(@PathParam("type") String type,
                                  @PathParam("receiverId") String receiverId) {

        List tasks = receiverService.receiverTasks(type, receiverId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tasks", JSONArray.parseArray(JSON.toJSONString(tasks)));
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    @POST
    @Path("/task/{taskId}/receiver")
    @Produces(MediaType.APPLICATION_JSON)
    public String receiverHandleTasks(@PathParam("taskId") String taskId,
                                   @FormParam("receiverId") String receiverId,
                                      @FormParam("token") String token) {
        int result = taskService.setReceiver(taskId, receiverId);
        String info = null;
        if(result == 0){
            info = "操作成功！";
        }else if(result == -1){
            info = "任务已被其他管理员受理！";
        }else{
            info = "任务不存在！";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", result);
        jsonObject.put("info", info);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

    @GET
    @Path("/rescuer/free")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFreeRescuer() {
        List rescuers = rescuerService.getFreeRescuer();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rescuers", JSONArray.parseArray(JSON.toJSONString(rescuers)));
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }

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
     * 添加救援人员到任务，并加入任务通信群组
     *
     * @param taskId      任务id
     * @param rescuerList 救援人员openid，可添加多个，以逗号分隔，第一个为救援队长
     */
    @POST
    @Path("/task/{taskId}/rescuer")
    @Produces(MediaType.APPLICATION_JSON)
    public String setTaskRescuer(@PathParam("taskId") String taskId,
                                 @FormParam("rescuerList") String rescuerList) {
        String[] rescuerOpenId = rescuerList.split(",");
        taskService.taskAddRescuer(taskId, rescuerOpenId[0]);
        for (String openId : rescuerOpenId) {
            taskService.imGroupAddRescuer(taskId, openId);
        }
        return "";
    }

    /**
     * 救援任务完结，并删除任务通信群组
     *
     * @param taskId 任务id
     * @return
     */
    @DELETE
    @Path("/task/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTask(@PathParam("taskId") String taskId) {
        String info = taskService.endTask(taskId);
        return info;
    }
}
