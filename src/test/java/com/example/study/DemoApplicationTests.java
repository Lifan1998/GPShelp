package com.example.study;


import com.example.study.service.impl.IMServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private IMServiceImpl imService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void userRegisterTest(){
		imService.userRegister("111","111");
	}

}

