package com.example.study.service;

import java.util.List;
import java.util.Map;

public interface ReceiverService {

    Map receiverLogin(String account, String password);

    Map receiverInfo(String receiverId);

    List receiverTasks(String type, String receiverId);
}
