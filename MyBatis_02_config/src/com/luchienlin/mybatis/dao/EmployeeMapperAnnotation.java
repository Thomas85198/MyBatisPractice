package com.luchienlin.mybatis.dao;

import org.apache.ibatis.annotations.Select;

import com.luchienlin.mybatis.bean.Employee;

public interface EmployeeMapperAnnotation {

	@Select("select * from tb1_employee where id=#{id}")
	public Employee getEmpById(Integer id);
}
