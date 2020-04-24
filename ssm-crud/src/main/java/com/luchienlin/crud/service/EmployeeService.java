package com.luchienlin.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luchienlin.crud.bean.Employee;
import com.luchienlin.crud.dao.EmployeeMapper;

@Service
public class EmployeeService  {

	// 呼叫DAO
	@Autowired
	EmployeeMapper employeeMapper;
	
	/**
	 * 查詢所有員工數據
	 * @return
	 */
	public List<Employee> getAll(){
		// 全部都要沒有查詢條件，就放null
		return employeeMapper.selectByExample(null); 
	}
	
	
}
