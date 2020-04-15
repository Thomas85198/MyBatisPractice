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

/**
 * 
 * 1. 接口式編程
 * 	原生：    Dao ===> DaoImpl
 * 	mybatis：   Mapper ===> xxMapper.xml
 * 2. SqlSession代表和資料庫的一次Session，用完必須關閉(但現在好像不用)
 * 3. SqlSession和connection一樣是多執行續。每次使用都應該去獲取新對象。
 * 4. mapper接口沒有實現類，但是mybatis會為這個街口生成一個代理對象
 * 	(將接口和xml進行綁定)
 * 	EmployeeMapper empMapper = sqlSession.getMapper(EmployeeMapper.class)
 * 5. 兩個重要的配置文件：包含數據庫連結池訊息，事務管理器訊息等...系統運行環境訊息
 * 	sql映射文件：保存了每一個sql語句的映射訊息。
 * 						將sql抽取出來。
 * 	mybatis
 * @author Thomas Lu
 *
 */
public class MyBatisTest {

	
	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml"; // 放文件名
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	/**
	 * 1. 根據xml配置文件(全局配置文件)建立一個SqlSessionFactory物件
	 * 	有數據源一歇運行環境訊息
	 * 2. sql映射文件：配置每一個sql，以及sql的封裝規則等。
	 * 3. 將sql映射文件註冊在全局配置文件中
	 * 4. 寫程式碼
	 * 		1) 根據全局配置文件得到SqlSessionFactory
	 * 		2) 使用sqlSession工廠，獲取到sqlSession對象使用他來執行CRUD
	 * 			一個sqlSession就代表一次Session，用完關閉
	 * 		3) 使用sql的唯一標誌(id)來告訴MyBatis執行哪sql。sql都保持在sql映射文件中的。
	 * @throws IOException
	 */
	@Test
	public void test() throws IOException {
		String resource = "mybatis-config.xml"; // 放文件名
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		// 2.獲取sqlSession實例，能直接執行已經映射的sql語句
		// sql的唯一標示：statement Unique identifier matching the statement to use.
		// 執行sql要用的參數parameter A parameter object to pass to the statement.
		SqlSession openSession = sqlSessionFactory.openSession();
		try{
			Employee employee = openSession.selectOne("com.luchienlin.mybatis.EmployeeMapper.selectEmp", 1); // 裡面CRUD都有
			System.out.println(employee); 
		}finally {
			openSession.close();
		}

	}
	
	
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
