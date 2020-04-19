package com.luchienlin.mybatis.dao;

import com.luchienlin.mybatis.bean.Department;

public interface DepartmentMapper {
	
	// 創建dept的Interface
	public Department getDeptById(Integer id);
	
	// 增強版的查詢部門，可以把部門內的員工都查詢出來
	public Department getDeptByIdPlus(Integer id);
	
	// 寫分佈查詢，不像上面的那麼辛苦用join
	public Department getDeptByIdStep(Integer id);
}
