package com.luchienlin.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import com.luchienlin.mybatis.bean.Department;
import com.luchienlin.mybatis.bean.Employee;
import com.luchienlin.mybatis.dao.DepartmentMapper;
import com.luchienlin.mybatis.dao.EmployeeMapper;
import com.luchienlin.mybatis.dao.EmployeeMapperAnnotation;
import com.luchienlin.mybatis.dao.EmployeeMapperDynamicSQL;
import com.luchienlin.mybatis.dao.EmployeeMapperPlus;

/**
 * 
 * 1. 接口式編程 原生： Dao ===> DaoImpl mybatis： Mapper ===> xxMapper.xml 2.
 * SqlSession代表和資料庫的一次Session，用完必須關閉(但現在好像不用) 3.
 * SqlSession和connection一樣是多執行續。每次使用都應該去獲取新對象。 4.
 * mapper接口沒有實現類，但是mybatis會為這個街口生成一個代理對象 (將接口和xml進行綁定) EmployeeMapper empMapper
 * = sqlSession.getMapper(EmployeeMapper.class) 5.
 * 兩個重要的配置文件：包含數據庫連結池訊息，事務管理器訊息等...系統運行環境訊息 sql映射文件：保存了每一個sql語句的映射訊息。 將sql抽取出來。
 * mybatis
 * 
 * @author Thomas Lu
 *
 */
public class MyBatisTest {

	public SqlSessionFactory getSqlSessionFactory() throws IOException {
		String resource = "mybatis-config.xml"; // 放文件名
		InputStream inputStream = Resources.getResourceAsStream(resource);
		return new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void testDynamicSql() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
			Employee employee = new Employee(1, "%e%", null, "0");
			List<Employee> emps = mapper.getEmpsByConditionIf(employee);
			for(Employee emp: emps) {
				System.out.println(emp);
			}
		}finally {
			openSession.close();
		}
	}

	/**
	 * 1. 根據xml配置文件(全局配置文件)建立一個SqlSessionFactory物件 有數據源一歇運行環境訊息 2.
	 * sql映射文件：配置每一個sql，以及sql的封裝規則等。 3. 將sql映射文件註冊在全局配置文件中 4. 寫程式碼 1)
	 * 根據全局配置文件得到SqlSessionFactory 2) 使用sqlSession工廠，獲取到sqlSession對象使用他來執行CRUD
	 * 一個sqlSession就代表一次Session，用完關閉 3)
	 * 使用sql的唯一標誌(id)來告訴MyBatis執行哪sql。sql都保持在sql映射文件中的。
	 * 
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
		try {
			Employee employee = openSession.selectOne("com.luchienlin.mybatis.EmployeeMapper.selectEmp", 1); // 裡面CRUD都有
			System.out.println(employee);
		} finally {
			openSession.close();
		}

	}

	@Test
	public void test01() throws IOException {
		// 1. 獲取sqlSessionFactory對象
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(); // 寫成方法在上面
		// 2.獲取sqlSession對象
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			// 3. 獲取街口的實現對象
			// 會為接口自動創建一個代理對象，代理對象去執行增刪改查方法
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			Employee employee = mapper.getEmpById(1); // 呼叫接口方法得到id
			System.out.println(mapper.getClass());
			System.out.println(employee);
		} finally {
			openSession.close();
		}

	}

	/**
	 * 測試沒SQL配置文件的...
	 * 
	 * @throws IOException
	 */
	@Test
	public void test02() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
			Employee empById = mapper.getEmpById(1);
			System.out.println(mapper.getClass());
			System.out.println(empById);
		} finally {
			openSession.close();
		}
	}

	/**
	 * 測試增刪改 1. mybatis允許增刪改查直接定義以下類型返回值 Integer、Long、Boolean
	 * 
	 * 2. 我們需要手動提交數據 sqlSessionFactory.openSessionFactory = openSession() ===> 手動提交
	 * sqlSessionFactory.openSessionFactory = openSession(true) ===> 自動提交
	 * 
	 * @throws IOException
	 */
	@Test
	public void test03() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 1.獲取到的SqlSession不會自動提交
		SqlSession openSession = sqlSessionFactory.openSession();
		try {

			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			// 測試添加
			Employee employee = new Employee(null, "jerry3", null, "1");
			mapper.addEmp(employee);
			System.out.println(employee.getId());

			// 測試修改
			// Employee employee = new Employee(1, "Tom", "Thomas2793@yahoo.com.tw", "0");
			// boolean updateEmp = mapper.updateEmp(employee);
			// System.out.println(updateEmp);

			// 測試刪除
			// mapper.deleteEmpById(2);

			// 2. 手動提交
			openSession.commit();
		} finally {
			openSession.close();
		}

	}

	@Test
	public void test04() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		// 1.獲取到的SqlSession不會自動提交
		SqlSession openSession = sqlSessionFactory.openSession();
		try {
			EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
			// Employee employee = mapper.getEmpByIdAndLastName(1, "Tom");
			/*
			 * Map<String, Object> map = new HashMap<>(); map.put("id", 1);
			 * map.put("lastName", "Tom"); map.put("tableName", "tb1_employee"); Employee
			 * employee = mapper.getEmpByMap(map); System.out.println(employee);
			 */
			// 查出名字帶e字母的
			List<Employee> like = mapper.getEmpsByLastNameLike("%e%");
			for (Employee emp : like) {
				System.out.println(emp);
			}

			// {gender=0, last_name=Tom, id=1, email=Thomas2793@yahoo.com.tw}
			Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
			System.out.println(map);

			Map<Integer, Employee> map2 = mapper.getEmpByLastNameLikeReturnMap("Jerry");
			System.out.println(map2);

		} finally {
			openSession.close();
		}

	}

	@Test
	public void test05() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();

		try {
			EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
			/*Employee empById = mapper.getEmpById(1);
			System.out.println(empById);*/
			/*Employee empAndDept = mapper.getEmpAndDept(1);
			System.out.println(empAndDept);
			System.out.println(empAndDept.getDept());*/
			Employee empByIdStep = mapper.getEmpByIdStep(1);
			System.out.println(empByIdStep);
			System.out.println(empByIdStep.getDept().getDepartmentName()); // 要用到才查詢
			// System.out.println(empByIdStep.getDept());
		} finally {
			openSession.close();
		}
	}
	
	/**
	 * 1. 加強版的集合查詢部門
	 * 可以查詢部門的所有員工
	 * 2. 查詢特定員工的分佈查詢
	 * @throws IOException
	 */
	@Test
	public void test06() throws IOException {
		SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
		SqlSession openSession = sqlSessionFactory.openSession();
		
		try {
			DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
//			Department department = mapper.getDeptByIdPlus(1);
//			System.out.println(department);
//			System.out.println(department.getEmps());
			Department deptByIdStep = mapper.getDeptByIdStep(1);
			System.out.println(deptByIdStep.getDepartmentName());
			System.out.println(deptByIdStep.getEmps());
		} finally {
			openSession.close();
		}
		
	}

}
