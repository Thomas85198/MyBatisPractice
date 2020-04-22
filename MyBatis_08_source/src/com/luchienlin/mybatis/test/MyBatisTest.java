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
	 * 1. 獲取sqlSessionFactory對象：解析文件的每一個訊息保存在Configuration中，返回包含Configuration的Defaults中
	 * 		注意：【MappedStatement】：代表一個增刪改查的詳細訊息
	 * 2. 獲取sqlSesion對象
	 * 3. 獲取接口的代理對象
	 * 4. 執行增刪改查方法
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


}
