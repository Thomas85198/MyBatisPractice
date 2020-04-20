package com.luchienlin.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.luchienlin.mybatis.bean.Employee;
import com.luchienlin.mybatis.dao.EmployeeMapper;


public class MyBatisTest {
	

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 兩級緩存
	 * 一級緩存：(本地緩存)：sqlSession級別的緩存的一個Map：與數據庫同一次會話期間查詢到數據會放到本地緩存中。
	 * 以後如果需要獲取相同數據，直接從緩存中拿，沒必要再去查詢數據庫
	 * 
	 * 一級緩存失效情況(沒有使用到當前一級緩存的情況，效果就是，還需要再向數據庫發出查詢)：
	 * 1.sqlSession不同。
	 * 2.sqlSession相同，查詢條件不同。(當前一級緩存中還沒有這個數據)
	 * 3.sqlSession相同，兩次查詢條件執行了增刪改操作。(這次增刪改可能對當前數據有影響)
	 * 4.sqlSession相同，手動清除了一級緩存(緩存清空)
	 * 
	 * 二級緩存(全局緩存)：基於namespace級別的緩存：一個namespace對應一個二級緩存：
	 * 			工作機制：
	 * 			1. 一個會話，查詢一條數據，這個數據就會被放在當前會話的一級緩存中：
	 * 			2. 如果會話關閉：一級緩存中的數據被保存到二級緩存中：新的會話查詢訊息，就可以參照二級緩存
	 * 			3. sqlSession===EmployeeMapper==>Employee
	 * 						DepartmentMapper===>Department
	 * 				不同namespace查出的數據會放在自己對應的緩存中(map中)
	 * 				效果：數據會從二級緩存中獲取
	 * 					查出數據都會被默認先放在一級緩存中。
	 * 					只有會話提交或者關閉之後，一級緩存中的數據才會轉到二級緩存中。
	 * 
	 * 			使用:
	 * 				1) 開啟全局二級緩存配置：<setting name="cacheEnabled" value="true"/>
	 * 				2) 去mapper.xml中配置使用二級緩存
	 * 					<cache></cache>
	 * 				3) 我們的POJO需要實現序列話接口
	 * 
	 * 和緩存有關係的設置/屬性：
	 * 			1) cacheEnabled=true：false：關閉緩存(二級緩存：關閉)(一級緩存一直可用的)
	 * 			2) 每個select標籤都有useCache="true"
	 * 					false：不使用緩存(一級緩存依然可以使用，二級緩存不使用)
	 * 			3) 【每個增刪改標籤：flushCache="true"；增刪改執行完成後就會清楚緩存：(一級二級都會清)】
	 * 					測試flushCache="true"：一級緩存就清空了：二級緩存也會被清除
	 * 					查詢標籤:flushCahce="false";
	 * 						如果flushCache=true;每次查詢之後都會清空緩存，緩存是沒有被使用的。
	 * 
	 * 			4) sqlSession.clearCache();只清除當前session的一級緩存
	 * 			5) localCacheScope：本地緩存作用域：(一級緩存SESSION)：當前會話的所有數據保存在會話緩存中
	 * 							STATEMENT：可以禁用一級緩存。
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testSecondLevelCache() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		SqlSession openSession2 = sqlSessionFactory.openSession();
		try {
			// 1.
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			EmployeeMapper mapper2 = openSession2.getMapper(EmployeeMapper.class);
			
			Employee emp01 = mapper.getEmpById(1);
			System.out.println(emp01);
			openSession.close();
			
			// 第二次查詢是從二級緩存中拿到的數據，並沒有重新發送新的sql
			//mapper2.addEmp(new Employee(null, "aaa", "nnn", "0"));
			Employee emp02 = mapper2.getEmpById(1);
			System.out.println(emp02);
			openSession2.close();
		}finally {
			
		}
	}
	
	
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
			
			// 3.sqlSession相同，兩次查詢條件執行了增刪改操作。(這次增刪改可能對當前數據有影響)
			//mapper.addEmp(new Employee(null, "testCache", "cache", "1"));
			//System.out.println("添加成功");
			
			// 4.sqlSession相同，手動清除了一級緩存(緩存清空)
			openSession.clearCache();
			
			Employee emp02 = mapper.getEmpById(1);
			//Employee emp03 = mapper.getEmpById(2);
			
			//System.out.println(emp03);
			System.out.println(emp02);
			System.out.println(emp01 == emp02);
			

			
		} finally {
			
			openSession.close();
			
		}
	}
}
