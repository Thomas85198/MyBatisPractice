package com.luchienlin.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.luchienlin.mybatis.bean.Department;
import com.luchienlin.mybatis.bean.Employee;
import com.luchienlin.mybatis.dao.DepartmentMapper;
import com.luchienlin.mybatis.dao.EmployeeMapper;
import com.luchienlin.mybatis.dao.EmployeeMapperAnnotation;
import com.luchienlin.mybatis.dao.EmployeeMapperDynamicSQL;
import com.luchienlin.mybatis.dao.EmployeeMapperPlus;


public class MyBatisTest {
	

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 兩級緩存
	 * 一級緩存：(本地緩存)：sqlSession級別的緩存：與數據庫同一次會話期間查詢到數據會放到本地緩存中。
	 * 以後如果需要獲取相同數據，直接從緩存中拿，沒必要再去查詢數據庫
	 * 
	 * 一級緩存失效情況(沒有使用到當前一級緩存的情況，效果就是，還需要再向數據庫發出查詢)：
	 * 1.sqlSession不同。
	 * 2.sqlSession相同，查詢條件不同。
	 * 
	 * 二級緩存(全局緩存)
	 * 
	 * 
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testFirstLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee emp01 = mapper.getEmpById(1);
			System.out.println(emp01);
			
			// XXX
			// 1. sqlSession不同
			//SqlSession openSession2 = sqlSessionFactory.openSession();
			//EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
			
			// 2. sqlSession相同，查詢條件不同
			Employee emp02 = mapper.getEmpById(3);
			System.out.println(emp02);
			System.out.println(emp01 == emp02);
			

			
		} finally {
			
			openSession.close();
			
		}
	}
}
