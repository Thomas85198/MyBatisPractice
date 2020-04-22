package com.luchienlin.mybatis.dao;

import java.util.List;

import com.luchienlin.mybatis.bean.Employee;

// 用來查出Employee數據的id
public interface EmployeeMapper {

	public Employee getEmpById(Integer id);
	
	// 查詢所有員工
	public List<Employee> getEmps();
	
	
	public Long addEmp(Employee employee);
}
