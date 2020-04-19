package com.luchienlin.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.luchienlin.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {

	public List<Employee> getEmpsTestInnerParameter(Employee employee);

	/**
	 * 查詢員工，要求，攜帶了哪個字段查詢條件就戴上字段的值
	 * 
	 * @param employee
	 * @return
	 */
	public List<Employee> getEmpsByConditionIf(Employee employee);

	// 優化where標籤：可以在中間寫and不會有問題
	public List<Employee> getEmpsByConditionTrim(Employee employee);

	// Choose
	public List<Employee> getEmpsByConditionChoose(Employee employee);

	// 員工id更新employee對象(set)
	public void updateEmp(Employee employee);

	// 實現forEach
	public List<Employee> getEmpsByConditionForeach(List<Integer> ids);

	// 實現forEach批量保存
	public void addEmps(@Param("emps") List<Employee> emps);
}
