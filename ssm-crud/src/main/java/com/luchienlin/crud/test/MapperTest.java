package com.luchienlin.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.luchienlin.crud.bean.Department;
import com.luchienlin.crud.bean.Employee;
import com.luchienlin.crud.dao.DepartmentMapper;
import com.luchienlin.crud.dao.EmployeeMapper;

/**
 * 測試 dao 層的工作
 * 
 * @author Thomas Lu 推薦Spring的項目就可以使用Spring的單元測試，可以自動注入我們需要的組件 1. 導入SpringTest模組
 *         2. @ContextConfiguration指定Spring配置文件的位置 3. 直接autowired要使用的組件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class MapperTest {

	@Autowired
	DepartmentMapper departmentMapper;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	SqlSession sqlSession;

	/**
	 * 測試DepartmentMapper
	 */
	@Test
	public void testCRUD() {
//		// 1.創建Spring IOC容器
//		ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//		// 2.從容器中獲取Mapper
//		DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
		
		System.out.println(departmentMapper);
		
		// 1. 插入幾個部門
//		departmentMapper.insertSelective(new Department(null, "開發部"));
//		departmentMapper.insertSelective(new Department(null, "測試部"));
		
		// 2. 生成員工數據，測試員工插入
//		employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@gmail.com", 1));	
		
		// 3. 批量插入多個員工：批量，使用可以執行批量操作的sqlSession
		/*for() {
			employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@gmail.com", 1));	
		}*/
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i=0; i<1000; i++) {
			String uid = UUID.randomUUID().toString().substring(0,5) + i;
			mapper.insert(new Employee(null, uid, "M", uid+"@gmail.com", 1));
		}
		
		
	}
}
