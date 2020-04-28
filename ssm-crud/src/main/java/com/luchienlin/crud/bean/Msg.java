package com.luchienlin.crud.bean;

import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.PageInfo;

/**
 * 
 * 通用的返回的類
 * @author Thomas Lu
 *
 */
public class Msg {
	// 狀態碼 100-成功 200-失敗(自訂的)
	private int code;
	// 提示訊息
	private String msg;
	
	// 用戶要返回給瀏覽器的數據
	private Map<String, Object> extend = new HashMap<String, Object>();

	// 請求成功了
	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setMsg("處裡成功!");
		return result;
	}
	
	// 請求失敗了
	public static Msg fail() {
		Msg result = new Msg();
		result.setCode(200);
		result.setMsg("處裡失敗!");
		return result;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}

	//　讓Msg除了可以返回數據也可以返回pageInfo對象
	public Msg add(String key, Object value) {
		this.getExtend().put(key, value);
		return this;
	}
	
	
	
}
