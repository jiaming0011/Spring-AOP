package cn.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.dao.BaseDao;
import cn.service.ServiceDao;

public class demo {
	@Test
	public void demo01(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		BaseDao bd = (BaseDao) context.getBean("BaseDao");
		bd.say();
	}
	@Test
	public void demo02(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		context.start();
		ServiceDao se = (ServiceDao) context.getBean("ServiceDao");
		se.service();
		context.destroy();
		context.close();
	}
}
