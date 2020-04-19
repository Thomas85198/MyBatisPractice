package com.luchienlin.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.luchienlin.mybatis.bean.Employee;

// 用來查出Employee數據的id
public interface EmployeeMapper {

	// 多條紀錄封裝一個map，Map<Integer, Employee>：鍵是這條紀錄的主鍵，值是紀錄封裝的java
	// 告訴myBatis封裝這個map的時候使用哪個屬性為map的key
	@MapKey("id")
	public Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);
	
	// 返回一條紀錄的map：key就是列名，值就是對應的值
	public Map<String, Object> getEmpByIdReturnMap(Integer id);

	// 如果返回是List
	public List<Employee> getEmpsByLastNameLike(String lastName);
	
	// 如果沒有業務邏輯相對應的
	public Employee getEmpByMap(Map<String, Object> map);
	
	// 測試多個參數
	public Employee getEmpByIdAndLastName(@Param("id")Integer id,  @Param("lastName")String lastName);
	
	// 查
	public Employee getEmpById(Integer id);
	
	// 增
	public Long addEmp(Employee employee);
	
	// 改
	public boolean updateEmp(Employee employee);
	
	// 刪
	public void deleteEmpById(Integer id);
}
