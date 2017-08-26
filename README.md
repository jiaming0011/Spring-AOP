# Spring-AOP学习总结
### 什么是AOP
AOP:面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术<br>
功能例如：日志记录，性能统计，安全控制，事务处理，异常处理等等
### AOP实现方式
* 预编译<br>
-AspectJ
* 运行期动态代理<br>
-SpringAOP,JbossAOP
### 几个相关的概念
名称 | 说明 |
--------- | --------|
切面（Aspect）  | 一个关注点的模块话，这个关注点可能会横切多个对象 |
连接点（Joinpoint）  | 程序执行过程中某个特定的点 |
通知（Advice）  | 在切面的某个特定的连接点上执行的动作 |
切入点（Pointcut）  | 匹配连接点的断言，在AOP中通知和一个切入点表达式关联 |
引入（Introduction）  | 在不修改类代码的前提下，为类添加新的方法和属性 |
目标对象（Target Object）  | 被一个或者多个切面所通知的对象 |
AOP代理（AOP Proxy）  | Aop框架创建的对象，用来实现切面契约 |
织入（Weaving）  | 把切面连接到其他的应用程序类型或者对象上，并且创建一个被通知的对象，分为<br>编译时织入，类加载时织入，执行时织入 |
### AOP术语通俗解释
* 通知、增强处理（Advice） 就是你想要的功能，也就是上说的安全、事物、日子等。你给先定义好，然后再想用的地方用一下。包含Aspect的一段处理代码
* 连接点（JoinPoint） 这个就更好解释了，就是spring允许你是通知（Advice）的地方，那可就真多了，基本每个方法的钱、后（两者都有也行），或抛出异常是时都可以是连接点，spring只支持方法连接点。其他如AspectJ还可以让你在构造器或属性注入时都行，不过那不是咱们关注的，只要记住，和方法有关的前前后后都是连接点。
* 切入点（Pointcut） 上面说的连接点的基础上，来定义切入点，你的一个类里，有15个方法，那就有十几个连接点了对吧，但是你并不想在所有方法附件都使用通知（使用叫织入，下面再说），你只是想让其中几个，在调用这几个方法之前、之后或者抛出异常时干点什么，那么就用切入点来定义这几个方法，让切点来筛选连接点，选中那几个你想要的方法。
* 切面（Aspect） 切面是通知和切入点的结合。现在发现了吧，没连接点什么事，链接点就是为了让你好理解切点搞出来的，明白这个概念就行了。通知说明了干什么和什么时候干（什么时候通过方法名中的befor，after，around等就能知道），二切入点说明了在哪干（指定到底是哪个方法），这就是一个完整的切面定义。
* 引入（introduction） 允许我们向现有的类添加新方法属性。这不就是把切面（也就是新方法属性：通知定义的）用到目标类中吗
* 目标（target） 引入中所提到的目标类，也就是要被通知的对象，也就是真正的业务逻辑，他可以在毫不知情的情况下，被咋们织入切面。二自己专注于业务本身的逻辑。
* 代理（proxy） 怎么实现整套AOP机制的，都是通过代理。
* 织入（weaving） 把切面应用到目标对象来创建新的代理对象的过程。有三种方式，spring采用的是运行时。
* 目标对象 – 项目原始的Java组件。
* AOP代理  – 由AOP框架生成java对象。
* AOP代理方法 = advice +　目标对象的方法。
### 代码演示
首先配置beans.xml<br>
```Java
<?xml version="1.0" encoding="UTF-8"?>
<beans
	xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans 
	                              http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	                              http://www.springframework.org/schema/context
	                              http://www.springframework.org/schema/context/spring-context-3.0.xsd
	                              http://www.springframework.org/schema/aop
	                              http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
	                              "
	default-autowire="no">
	<bean id="BaseDao" class="cn.dao.impl.BaseDaoImpl" scope="prototype">
	</bean>	
	<bean id="ServiceDao" class="cn.service.impl.ServiceDaoImpl">
	</bean> 
	<bean id="aspectDemo" class="cn.aspect.AspectDemo">
	</bean>  
	<aop:config>
		<aop:aspect id="aspectDemoAop" ref="aspectDemo">
			<aop:pointcut expression="execution(* cn.service.impl.ServiceDaoImpl.*(..))" id="aspectPointCut"/>
			<aop:before method="before" pointcut-ref="aspectPointCut"/>
			<aop:after-returning method="afterReturning" pointcut-ref="aspectPointCut"/>
			<aop:after-throwing method="afterThrowing" pointcut-ref="aspectPointCut"/>
			<aop:after method="after" pointcut-ref="aspectPointCut"/>
			<aop:around method="around" pointcut-ref="aspectPointCut"/>
		</aop:aspect>
	</aop:config>
</beans>
```
其中<aop:aspect id="aspectDemoAop" ref="aspectDemo">	</aop:aspect>是对切面的定义，要写在<aop:config>属性中，pointcut是切入点的意思
在expression写触发时会发生的方法事件，* cn.service.impl.ServiceDaoImpl.*(..)这一行代表的意思是在ServiceDaoImpl类下所有的方法执行都会触发。
<br>
<aop:before method="before" pointcut-ref="aspectPointCut"/><br>
<aop:after-returning method="afterReturning" pointcut-ref="aspectPointCut"/><br>
<aop:after-throwing method="afterThrowing" pointcut-ref="aspectPointCut"/><br>
<aop:after method="after" pointcut-ref="aspectPointCut"/><br>
<aop:around method="around" pointcut-ref="aspectPointCut"/><br>
这五行都是通知的不同类型分别是方法事件触发前执行，方法事件出发后返回执行，方法事件抛出异常后执行，方法结束后执行，方法环绕执行<br>
```java
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

```
运行结果：
```java
log4j:WARN No appenders could be found for logger (org.springframework.context.support.ClassPathXmlApplicationContext).
log4j:WARN Please initialize the log4j system properly.
通知前已响应
1
逻辑层判断
返回后响应
方法结束后响应
2
```
### after-returing和after-throwing和after
after-returing和after-throwing两者只能其中一个执行，若抛出异常则after-throwing，否则就after-returing，after则在两者之后执行，且与前两者是否执行无关。<br>
round的话必须在方法里写ProceedingJoinPoint参数才能执行
