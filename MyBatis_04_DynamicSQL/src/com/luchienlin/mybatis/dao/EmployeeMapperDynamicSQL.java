package com.luchienlin.mybatis.dao;

import java.util.List;

import com.luchienlin.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {

	/**
	 * 查詢員工，要求，攜帶了哪個字段查詢條件就戴上字段的值
	 * @param employee
	 * @return
	 */
	public List<Employee> getEmpsByConditionIf(Employee employee);
	
}
