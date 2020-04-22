package com.luchienlin.mybatis.dao;

import com.luchienlin.mybatis.bean.Employee;

// 用來查出Employee數據的id
public interface EmployeeMapper {

	public Employee getEmpById(Integer id);
}
