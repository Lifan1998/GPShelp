package com.example.study.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.study.service.IMService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.ws.rs.core.MediaType;


@Service
public class IMServiceImpl implements IMService {

    private final static Logger logger = LoggerFactory.getLogger(IMServiceImpl.class);

    @Override
    public String getToken() {
        ClientResponse response = null;
        String token = null;
        try {
            Client client = Client.create();
            WebResource resource = client.resource("http://a1.easemob.com/1110190505090244/gpshelp/token");
            resource.type(MediaType.APPLICATION_JSON_TYPE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("grant_type", "client_credentials");
            jsonObject.put("client_id", "YXA6X9oacG9AEemBu49-XheQkg");
            jsonObject.put("client_secret", "YXA692g8S7tELyeHGcIOzN1CV9qv1zs");
            response = resource.post(ClientResponse.class, JSONObject.toJSONString(jsonObject));
            int status = response.getStatus();
            String data = response.getEntity(String.class);
            logger.debug(data);
            if (status == 200) {
                jsonObject = JSON.parseObject(data);
                token = jsonObject.getString("access_token");
                logger.debug(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            return token;
        }
    }

    @Override
    public int userRegister(String account, String password) {
        ClientResponse response = null;
        int status = 0;
        try {
            Client client = Client.create();
            WebResource resource = client.resource("http://a1.easemob.com/1110190505090244/gpshelp/users");
            resource.type(MediaType.APPLICATION_JSON_TYPE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", account);
            jsonObject.put("password", password);
            response = resource.post(ClientResponse.class, JSONObject.toJSONString(jsonObject));
            status = response.getStatus();
            String data = response.getEntity(String.class);
            logger.debug(data);
            if (status == 200) {
                logger.debug("注册成功！用户id: " + account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            return status;
        }
    }

    @Override
    public String creatGroup(String groupName, String desc, String owner) {
        ClientResponse response = null;
        String groupId = null;
        try {
            Client client = Client.create();
            WebResource resource = client.resource("http://a1.easemob.com/1110190505090244/gpshelp/chatgroups");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("groupname", groupName);
            jsonObject.put("desc", desc);
            jsonObject.put("public", true);
            jsonObject.put("owner", owner);
            response = resource.header("Authorization", "Bearer " + getToken())
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .post(ClientResponse.class, JSONObject.toJSONString(jsonObject));
            int status = response.getStatus();
            String data = response.getEntity(String.class);
            logger.debug(data);
            if (status == 200) {
                jsonObject = JSON.parseObject(data);
                groupId = jsonObject.getJSONObject("data").getString("groupid");
                logger.debug("群建立成功！群id: " + groupId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            return groupId;
        }
    }

    @Override
    public int groupAddUser(String groupId, String userName) {
        ClientResponse response = null;
        int status = 0;
        try {
            Client client = Client.create();
            WebResource resource = client.resource("http://a1.easemob.com/1110190505090244/gpshelp/chatgroups/" + groupId+"/users/" + userName);
            response =resource.header("Authorization", "Bearer " + getToken())
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .post(ClientResponse.class);
            status = response.getStatus();
            String data = response.getEntity(String.class);
            logger.debug(data);
            if (status == 200) {
                logger.debug("群成员添加成功！群id: " + groupId + " 用户id: " + userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            return status;
        }
    }

    @Override
    public int deleteGroup(String groupId) {
        ClientResponse response = null;
        int status = 0;
        try {
            Client client = Client.create();
            WebResource resource = client.resource("http://a1.easemob.com/1110190505090244/gpshelp/chatgroups/" + groupId);
            response =resource.header("Authorization", "Bearer " + getToken())
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .delete(ClientResponse.class);
            status = response.getStatus();
            String data = response.getEntity(String.class);
            logger.debug(data);
            if (status == 200) {
                logger.debug("群删除成功！群id: " + groupId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            return status;
        }
    }
}
