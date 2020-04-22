package com.luchienlin.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.luchienlin.mybatis.bean.Employee;
import com.luchienlin.mybatis.dao.EmployeeMapper;

public class MyBatisTest {

	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml"; // 放文件名
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}

	
	/**
	 * 1、获取sqlSessionFactory对象:
	 * 		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
	 * 		注意：【MappedStatement】：代表一个增删改查的详细信息
	 * 
	 * 2、获取sqlSession对象
	 * 		返回一个DefaultSQlSession对象，包含Executor和Configuration;
	 * 		这一步会创建Executor对象；
	 * 
	 * 3、获取接口的代理对象（MapperProxy）
	 * 		getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
	 * 		代理对象里面包含了，DefaultSqlSession（Executor）
	 * 4、执行增删改查方法
	 * 
	 * 总结：
	 * 	1、根据配置文件（全局，sql映射）初始化出Configuration对象
	 * 	2、创建一个DefaultSqlSession对象，
	 * 		他里面包含Configuration以及
	 * 		Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
	 *  3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
	 *  4、MapperProxy里面有（DefaultSqlSession）；
	 *  5、执行增删改查方法：
	 *  		1）、调用DefaultSqlSession的增删改查（Executor）；
	 *  		2）、会创建一个StatementHandler对象。
	 *  			（同时也会创建出ParameterHandler和ResultSetHandler）
	 *  		3）、调用StatementHandler预编译参数以及设置参数值;
	 *  			使用ParameterHandler来给sql设置参数
	 *  		4）、调用StatementHandler的增删改查方法；
	 *  		5）、ResultSetHandler封装结果
	 *  注意：
	 *  	四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
	 * 
	 * @throws IOException
	 */
	@Test
	public void test01() throws IOException {
		// 1. 獲取sqlSessionFactory對象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();	// 寫成方法在上面
		// 2.獲取sqlSession對象
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
		// 3. 獲取街口的實現對象
		// 會為接口自動創建一個代理對象，代理對象去執行增刪改查方法
		EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
		Employee employee = mapper.getEmpById(1); // 呼叫接口方法得到id
		System.out.println(mapper.getClass());
		System.out.println(employee);
		}finally {
			openSession.close();
		}
		
	}
	
	/**
	 * 在四大對象創建的時候
	 * 1. 每個創建出來的對象不是直接返回的，而是
	 * interceptorChain.pluginAll(parameterHandler);
	 * 
	 * 2. 獲取到所有的Interceptor(攔截器)(插件需要實現的接口)：
	 * 		調用interceptor.plugin(target);返回target包裝類的對象
	 * 3. 插件機制：我們可以使用插劍為目標對象創建一個代理對象：AOP(面向切面)
	 * 		我們的插件可以為四大對象創建出代理對象：
	 * 		代理對象就可以攔截到四大對象的每一個執行
	 * 
	 * 
	 */
	/**
	 * 插件編寫：
	 * 1. 編寫Interceptor的實現類
	 * 2. 使用@Intercepts註解完成插件簽名
	 * 3. 將寫好的插件註冊到全局配置文件中
	 */
	@Test
	public void testPlugin() {
		
		
		
	}

}
