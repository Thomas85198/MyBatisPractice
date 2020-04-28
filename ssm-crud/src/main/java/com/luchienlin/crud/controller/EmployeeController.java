package com.luchienlin.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luchienlin.crud.bean.Employee;
import com.luchienlin.crud.bean.Msg;
import com.luchienlin.crud.service.EmployeeService;

/**
 * 處裡員工CRUD請求
 * @author Thomas Lu
 *
 */
@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 導入jackson包
	 * @param pn
	 * @return
	 */
	// 需要返回PageInfo，ResponseBody可以做到
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn", defaultValue="1")Integer pn) {
		
		// 這不是一個分頁查詢：
		// 引入PageHelper分頁插件
		// 在查詢之前只需要調用(從第幾頁開始查，每頁的大小)
		PageHelper.startPage(pn, 5); // 頁碼、每頁顯示數量
				
		// startPage後面緊跟的這個查詢就是一個分頁查詢
		List<Employee> emps = employeeService.getAll();
				
		// 使用pageInfo包裝查詢後的結果，只需要將pageInfo交給頁面就行了。
		// 封裝了詳細的分頁訊息，包括有我們查出來的數據，傳入連續顯示的頁數
		PageInfo page = new PageInfo(emps, 5); // page的結果、頁碼數量 
		
		// Msg為一個返回通用屬性，可以知道
		return Msg.success().add("pageInfo", page);
	}
	
	
	
	
	/**
	 * 查詢員工數據(分頁查詢)
	 * @return
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn", defaultValue="1")Integer pn
			, Model model) {
		
		// 這不是一個分頁查詢：
		// 引入PageHelper分頁插件
		// 在查詢之前只需要調用(從第幾頁開始查，每頁的大小)
		PageHelper.startPage(pn, 5); // 頁碼、每頁顯示數量
		
		// startPage後面緊跟的這個查詢就是一個分頁查詢
		List<Employee> emps = employeeService.getAll();
		
		// 使用pageInfo包裝查詢後的結果，只需要將pageInfo交給頁面就行了。
		// 封裝了詳細的分頁訊息，包括有我們查出來的數據，傳入連續顯示的頁數
		PageInfo page = new PageInfo(emps, 5); // page的結果、頁碼數量 
		
		model.addAttribute("pageInfo", page);		
		return "list";
		
	}
}
