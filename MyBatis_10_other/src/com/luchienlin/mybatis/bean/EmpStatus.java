package com.luchienlin.mybatis.bean;

/**
 * 希望數據庫保存的是100,200這些代碼，而不是默認0,1或者枚舉類的名
 * @author Thomas Lu
 *
 */
public enum EmpStatus {

	LOGIN(100, "用戶登入"),LOGOUT(200, "用戶登出"),REMOVE(300,"用戶不存在");
	private Integer code;
	private String msg;
	
	private EmpStatus(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
		
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	// 按照狀態碼返回枚舉對象
	public static EmpStatus getEmpStatusByCode(Integer code) {
		switch(code) {
		case 100:
			return LOGIN;
		case 200:
			return LOGOUT;
		case 300:
			return REMOVE;
		default:
			return LOGOUT;
		}

		
	}
	
}
