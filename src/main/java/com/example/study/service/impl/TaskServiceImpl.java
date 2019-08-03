package com.example.study.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.study.mapper.RescuerMapper;
import com.example.study.mapper.TaskMapper;
import com.example.study.mapper.UserMapper;
import com.example.study.service.IMService;
import com.example.study.service.RescuerService;
import com.example.study.service.TaskService;

import java.util.*;

import com.example.study.object.Task;
import com.example.study.object.User;
import com.example.study.object.Rescuer;
import com.example.study.service.UserService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.internal.guava.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;

@Service
public class TaskServiceImpl implements TaskService {

    private final static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RescuerMapper rescuerMapper;

    @Autowired
    private IMService imService;

    @Autowired
    private RescuerService rescuerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${qqmap.apikey}")
    private String apikey;


    @Override
    public String createTask(String eventLocationName, String eventLocationCoordinate, String aidLocationName, String aidLocationCoordinate, String aidType, String openId) {
        User user = userMapper.selectByPrimaryKey(openId);
        if(user.getTaskId() != null){
            logger.debug("用户存在进行中的任务！任务ID：" + user.getTaskId());
            return user.getTaskId();
        }else{
            Task task = new Task();
            task.setId(String.valueOf(System.currentTimeMillis()));
            task.setStatus("受理中");
            task.setType(aidType);
            task.setUserOpenId(openId);
            task.setEventLocationName(eventLocationName);
            task.setEventLocationCoordinate(eventLocationCoordinate);
            task.setAidLocationName(aidLocationName);
            task.setAidLocationCoordinate(aidLocationCoordinate);
            task.setChatGroupId(imGroupCreate(task.getId(), task.getId(), openId));
            taskMapper.insert(task);
            logger.debug("救援任务建立！任务ID：" + task.getId());
            user.setTaskId(task.getId());
            userMapper.updateByPrimaryKey(user);
            return task.getId();
        }
    }

    @Override
    public Map getTaskInfo(String taskId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        Map taskInfo = new HashMap();
        taskInfo.put("status", task.getStatus());
        taskInfo.put("rescuerName", null);
        taskInfo.put("rescuerPhone", null);
        taskInfo.put("eventLocationName", task.getEventLocationName());
        taskInfo.put("eventLocationCoordinate", task.getEventLocationCoordinate());
        taskInfo.put("aidLocationName", task.getAidLocationName());
        taskInfo.put("aidLocationCoordinate", task.getAidLocationCoordinate());
        taskInfo.put("type", task.getType());
        taskInfo.put("aidRoute", task.getAidRoute());
        taskInfo.put("chatGroupId", task.getChatGroupId());
        if (task.getRescuerOpenId() != null && !task.getRescuerOpenId().equals("")){
            Rescuer rescuer = rescuerMapper.selectByOpenId(task.getRescuerOpenId());
            taskInfo.put("rescuerName", rescuer.getName());
            taskInfo.put("rescuerPhone", rescuer.getPhone());
        }
        if (task.getAidRoute() == null){
            logger.debug("任务ID: " +  taskId + " 调用路线规划");
            task.setAidRoute(getAidRoute(taskId, task.getEventLocationCoordinate()));
            taskMapper.updateByPrimaryKey(task);
        }
        return taskInfo;
    }

    @Override
    public void taskAddRescuer(String taskId, String openId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        task.setRescuerOpenId(openId);
        task.setStatus("进行中");
        taskMapper.updateByPrimaryKey(task);
    }

    @Override
    public String imGroupCreate(String groupName, String desc, String openId) {
        final Base64.Encoder encoder = Base64.getEncoder();
        String owner = encoder.encodeToString(openId.getBytes()).replace("=","");
        return imService.creatGroup(groupName, desc, owner);
    }

    @Override
    public String imGroupAddRescuer(String taskId, String openId) {
        rescuerService.setTaskId(openId, taskId);
        final Base64.Encoder encoder = Base64.getEncoder();
        String user = encoder.encodeToString(openId.getBytes()).replace("=","");
        Task task = taskMapper.selectByPrimaryKey(taskId);
        String groupId = task.getChatGroupId();
        int code = imService.groupAddUser(groupId, user);
        if(code == 200){
            return "操作成功！";
        }else{
            return "操作失败！";
        }
    }

    @Override
    public void setRescuerLocation(String taskId, String Coordinate, String openId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        if(task != null && task.getRescuerOpenId().equals(openId)){
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            ClientResponse response = null;
            int status = 0;
            try {
                Client client = Client.create();
                WebResource resource = client.resource("https://apis.map.qq.com/ws/direction/v1/driving/?from=" + task.getEventLocationCoordinate() + "&to=" + Coordinate + "&output=json&key=" + apikey);
                resource.type(MediaType.APPLICATION_JSON_TYPE);
                response = resource.get(ClientResponse.class);
                status = response.getStatus();
                String data = response.getEntity(String.class);
                logger.debug(data);
                if (status == 200) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = JSON.parseObject(data);
                    String distance  = String.valueOf(jsonObject.getJSONObject("result").getJSONArray("routes").getJSONObject(0).getInteger("distance"));
                    String duration  = String.valueOf(jsonObject.getJSONObject("result").getJSONArray("routes").getJSONObject(0).getInteger("duration"));
                    operations.set(taskId, Coordinate + "," + distance + "," + duration);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
            logger.debug("Redis taskId: " + taskId + "Coordinate: " + Coordinate);
        }
    }

    @Override
    public String getRescuerLocation(String taskId) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(taskId);
        if (hasKey) {
            logger.debug("Redis taskId: " + taskId + "Coordinate: " + operations.get(taskId));
            return operations.get(taskId);
        }
        logger.debug("Redis taskId: " + taskId + "Coordinate: null");
        return null;
    }

    @Override
    public String getAidRoute(String taskId, String eventLocationCoordinate) {
        if(getRescuerLocation(taskId) == null)
            return null;
        ClientResponse response = null;
        int status = 0;
        try {
            Client client = Client.create();
            WebResource resource = client.resource("https://apis.map.qq.com/ws/direction/v1/driving/?from=" + eventLocationCoordinate + "&to=" + getRescuerLocation(taskId).split(",")[0] + ',' + getRescuerLocation(taskId).split(",")[1] + "&output=json&key=" + apikey);
            resource.type(MediaType.APPLICATION_JSON_TYPE);
            response = resource.get(ClientResponse.class);
            status = response.getStatus();
            String data = response.getEntity(String.class);
            logger.debug(data);
            if (status == 200) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = JSON.parseObject(data);
                String polyline  = jsonObject.getJSONObject("result").getJSONArray("routes").getJSONObject(0).getJSONArray("polyline").toString();
                return polyline;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    @Override
    public String endTask(String taskId) {
        rescuerService.releaseRescuer(taskId);
        userService.releaseUser(taskId);
        Task task = taskMapper.selectByPrimaryKey(taskId);
        task.setStatus("已完结");
        taskMapper.updateByPrimaryKey(task);
        int code = imService.deleteGroup(task.getChatGroupId());
        if(code == 200){
            return "操作成功！";
        }else{
            return "操作失败！";
        }
    }

    @Override
    public int setReceiver(String taskId, String receiverId) {
        Task task = taskMapper.selectByPrimaryKey(taskId);
        if(task == null){
            return 1;
        }else if (task.getReceiverId() == null){
            task.setReceiverId(receiverId);
            taskMapper.updateByPrimaryKey(task);
            return 0;
        }else{
            return -1;
        }
    }
}
