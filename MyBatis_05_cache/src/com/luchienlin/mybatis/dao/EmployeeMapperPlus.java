package com.luchienlin.mybatis.dao;

import java.util.List;

import com.luchienlin.mybatis.bean.Employee;

public interface EmployeeMapperPlus {

		// 研究resultMap
		public Employee getEmpById(Integer id);
		
		// 研究resultMap，並結合部門的表
		public Employee getEmpAndDept(Integer id);
		
		// 獲取Employee
		public Employee getEmpByIdStep(Integer id);
		
		// 按照部門id查出所有員工，讓Department去使用
		public List<Employee> getEmpsByDeptId(Integer deptId);
}
