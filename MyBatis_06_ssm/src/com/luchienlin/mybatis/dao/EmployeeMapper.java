package com.luchienlin.mybatis.dao;

import java.util.List;

import com.luchienlin.mybatis.bean.Employee;


public interface EmployeeMapper {

	public Employee getEmpById(Integer id);
	
	public List<Employee> getEmps();
}
