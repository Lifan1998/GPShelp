package com.example.study.service.impl;

import com.example.study.mapper.ReceiverMapper;
import com.example.study.mapper.TaskMapper;
import com.example.study.mapper.UnitMapper;
import com.example.study.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import com.example.study.object.Receiver;
import com.example.study.object.Unit;
import com.example.study.object.Task;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class ReceiverServiceImpl implements ReceiverService {

    @Autowired
    private ReceiverMapper receiverMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public Map receiverLogin(String account, String password) {
        Map result = new HashMap();
        Receiver receiver = receiverMapper.selectByAccount(account);
        if(receiver != null && receiver.getAccountPassword().equals(password)){
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            operations.set(token, receiver.getId());
            result.put("info","登录成功");
            result.put("receiverId", receiver.getId());
            result.put("token", token);
        }else{
            result.put("info","登录失败");
        }
        return result;
    }

    @Override
    public Map receiverInfo(String receiverId) {
        Map result = new HashMap();
        Receiver receiver = receiverMapper.selectById(receiverId);
        if(receiver != null){
            Unit unit = unitMapper.selectByPrimaryKey(receiver.getUnitId());
            result.put("name", receiver.getName());
            result.put("gender", receiver.getGender());
            result.put("phone", receiver.getPhone());
            result.put("unitName", unit.getName());
        }
        return result;
    }

    @Override
    public List receiverTasks(String type, String receiverId) {
        List<Task> tasks = taskMapper.selectReceiverTasks(type, receiverId);
        List receiverTasks = new ArrayList<>();
        for(Task task:tasks){
            Map taskInfo = new HashMap();
            taskInfo.put("taskId", task.getId());
            taskInfo.put("taskStatus", task.getStatus());
            taskInfo.put("taskEventLocationName", task.getEventLocationName());
            taskInfo.put("taskAidLocationName", task.getAidLocationName());
            receiverTasks.add(taskInfo);
        }
        return receiverTasks;
    }
}
