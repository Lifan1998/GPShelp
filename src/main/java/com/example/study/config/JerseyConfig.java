package com.example.study.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Created by tigris on 2019/5/6.
 */
@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig{

    public JerseyConfig(){
        packages("com.example.study.controller.jersey");
    }
}
