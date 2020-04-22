package com.luchienlin.mybatis.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.luchienlin.mybatis.bean.Employee;
import com.luchienlin.mybatis.bean.EmployeeExample;
import com.luchienlin.mybatis.bean.EmployeeExample.Criteria;
import com.luchienlin.mybatis.dao.EmployeeMapper;

public class MyBatisTest {
	

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	@Test
	public void testMbg() throws Exception {
		   List<String> warnings = new ArrayList<String>();
		   boolean overwrite = true;
		   File configFile = new File("mbg.xml"); // 根目錄下
		   ConfigurationParser cp = new ConfigurationParser(warnings);
		   Configuration config = cp.parseConfiguration(configFile);
		   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		   myBatisGenerator.generate(null);
	}
	
	@Test
	public void testSimple() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			List<Employee> list = mapper.selectByExample(null);
			for(Employee emps: list) {
				System.out.println(emps.getId());
			}
		}finally {
			openSession.close();
		}
	}
	
	@Test
	public void testMyBatis3() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			// xxxExample就是封裝查詢條件的：查所有
			List<Employee> selectByExample = mapper.selectByExample(null);
			// 2.查詢員工名字中有e字母的，和員工性別是1的
			EmployeeExample example = new EmployeeExample();
			// 創建一個Criteria，這個Criteria就是拚裝查詢條件
			//  WHERE ( last_name like ? and gender = ? ) or( email like ? )
			Criteria criteria = example.createCriteria();
			criteria.andLastNameLike("%e%");
			criteria.andGenderEqualTo("1");
			
			Criteria criteria2 = example.createCriteria();
			criteria2.andEmailLike("%e%");
			example.or(criteria2);
			
			List<Employee> list = mapper.selectByExample(example);
			for (Employee employee : list) {
				System.out.println(employee.getdId());
			}			
		} finally {
			openSession.close();
		}
	}
}
