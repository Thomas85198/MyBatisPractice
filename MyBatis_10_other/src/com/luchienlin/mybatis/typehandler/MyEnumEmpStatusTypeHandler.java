package com.luchienlin.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.luchienlin.mybatis.bean.EmpStatus;

/**
 * 1. 實現TypeHandler接口，或者繼承BaseTypeHandler
 * @author Thomas Lu
 *
 */
public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus> {
	
	/**
	 * 定義當前數據如何保存到數據庫中
	 */
	@Override
	public void setParameter(PreparedStatement ps, int i, EmpStatus parameter, JdbcType jdbcType) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("要保存的狀態碼:" + parameter.getCode());
		ps.setString(i, parameter.getCode().toString()); // 枚舉對象的狀態碼
	}

	@Override
	public EmpStatus getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		// 需要根據從數據庫中拿到的枚舉類的狀態碼返回一個枚舉對象
		int code = rs.getInt(columnName);
		System.out.println("從數據庫中獲取狀態碼" + code);
		EmpStatus status = EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(ResultSet rs, int columnIndex) throws SQLException {
		int code = rs.getInt(columnIndex);
		System.out.println("從數據庫中獲取狀態碼" + code);
		EmpStatus status = EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	@Override
	public EmpStatus getResult(CallableStatement cs, int columnIndex) throws SQLException {
		int code = cs.getInt(columnIndex);
		System.out.println("從數據庫中獲取狀態碼" + code);
		EmpStatus status = EmpStatus.getEmpStatusByCode(code);
		return status;
	}

	

}
