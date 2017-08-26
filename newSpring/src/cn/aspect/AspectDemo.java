package cn.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

public class AspectDemo {
	public void before(){
		System.out.println("通知前已响应");
	}
	public void afterReturning(){
		System.out.println("返回后响应");
	}
	public void afterThrowing(){
		System.out.println("抛出异常后响应");
	}
	public void after(){
		System.out.println("方法结束后响应");
	}
	public Object around(ProceedingJoinPoint pjp){
		Object obj = null;
		try {
			System.out.println("1");
			obj = pjp.proceed();
			System.out.println("2");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
}
