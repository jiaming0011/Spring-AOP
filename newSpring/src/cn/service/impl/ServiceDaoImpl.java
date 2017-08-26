package cn.service.impl;

import cn.aspect.AspectDemo;
import cn.dao.BaseDao;
import cn.service.ServiceDao;

public class ServiceDaoImpl implements ServiceDao {

//	private BaseDao baseDao;
	//设值注入
//	public void setBaseDao(BaseDao baseDao) {
//		this.baseDao = baseDao;
//	}
	
	//构造器注入
//	public ServiceDaoImpl(BaseDao baseDao){
//	this.baseDao = baseDao;
//	}

	@Override
	public void service() {
		System.out.println("逻辑层判断");
//        throw new RuntimeException();
	}

}
