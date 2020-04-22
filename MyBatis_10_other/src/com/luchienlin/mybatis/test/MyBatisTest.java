package com.luchienlin.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luchienlin.mybatis.bean.EmpStatus;
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
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Page<Object> page = PageHelper.startPage(8, 1); // 第一頁的數據，只要五條紀錄
			
			List<Employee> emps = mapper.getEmps();
			// 插入要連續顯示多少頁連續顯示幾頁...第一個參數傳封裝的結果，第二個參數為分頁導航
			PageInfo<Employee> info = new PageInfo<Employee>(emps, 5); // 連續顯示5頁
			for (Employee employee : emps) {
				System.out.println(employee);
			}
			
//			System.out.println("當前頁碼：" + page.getPageNum());
//			System.out.println("總紀錄數：" + page.getTotal());
//			System.out.println("每頁的紀錄數：" + page.getPageSize());
//			System.out.println("總頁碼：" + page.getPages());
			//xxx
			System.out.println("當前頁碼："+info.getPageNum());
			System.out.println("當前筆數："+info.getTotal());
			System.out.println("當頁的紀錄數："+info.getPageSize());
			System.out.println("總頁碼："+info.getPages());
			System.out.println("是否第一頁："+info.isIsFirstPage());
			System.out.println("連續顯示的頁碼：");
			int[] nums = info.getNavigatepageNums(); // 3,4,5,6,7
			for(int i=0; i<nums.length; i++) {
				System.out.println(nums[i]);
			}
		}finally {
			openSession.close();
		}
		
	}

	@Test
	public void testBatch() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		
		// 可以執行批量操作
		SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		long start = System.currentTimeMillis();
		try {
		EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
		for(int i=0; i<10000; i++) {
			mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0,5), "b", "1"));
		}
		long end = System.currentTimeMillis();
		// 批量(預編譯sql一次==>設置參數===>10000次===>執行(1次)
		// Parameters：616c1(String), b(String), 1(String)===>459
		// 非批量(預編意sql=設置參數=執行)==>10000 10200
		
		System.out.println("執行時間：" + (end - start));
		openSession.commit();
		}finally {
			openSession.close();
		}
	}
	
	/**
	 * oracle分頁
	 * 		借助rownum：行號：子查詢：
	 * 存儲過程包裝分頁邏輯
	 */
	@Test
	public void testProcedure() {
		
	}
	
	@Test
	public void testEnumUse() {
		EmpStatus login = EmpStatus.LOGIN;
		System.out.println("枚舉的索引："+login.ordinal());
		System.out.println("枚舉的名字：" + login.name());
		
		System.out.println("枚舉的狀態碼：" + login.getCode());
		System.out.println("枚舉的提示消息：" + login.getMsg());
	}
	
	/**
	 * 默認mybatis在處裡枚舉對象的時候保存是枚舉的名字：EnumTypeHandler
	 * 改變使用EnumOrdinalTypeHandler
	 * @throws IOException
	 */
	@Test
	public void testEnum() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = new Employee("test_enum", "luchienlin@gmail.com", "1");
			// mapper.addEmp(employee);
			// System.out.println("保存成功" + employee.getId());
			Employee empById = mapper.getEmpById(30028);
			System.out.println(empById.getEmpStatus());
			openSession.commit();
		}finally {
			openSession.close();
		}
	}
}
