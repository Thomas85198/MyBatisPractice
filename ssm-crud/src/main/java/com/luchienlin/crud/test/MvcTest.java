package com.luchienlin.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.luchienlin.crud.bean.Employee;

/**
 * 使用Spring測試模塊提供的測試請功能，測試crud請求的正確性
 * Spring4測試時候，需要spring3.0的支持
 * @author Thomas Lu
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml" })
public class MvcTest {
	// 傳入Springmvc的ioc
	@Autowired
	WebApplicationContext context;
	// 虛擬的mvc請求，獲取到處理結果
	MockMvc mockMvc;
	
	@Before // junit的Before
	public void initMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		// 模擬請求拿到返回值 get請求，URL值，帶參數param
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
				.andReturn(); 
		
		// 請求成功以後，請求域中會有pageInfo，我們可以取出pageInfo進行驗證
		MockHttpServletRequest request = result.getRequest();
		PageInfo attribute = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("當前頁碼：" + attribute.getPageNum());
		System.out.println("總頁碼：" + attribute.getPages());
		System.out.println("總紀錄數：" + attribute.getTotal());
		System.out.println("在頁面需要連續顯示的頁碼");
		int [] nums = attribute.getNavigatepageNums();
		for(int i: nums) {
			System.out.print(" " + i);
		}
		
		// 獲得員工數據
		List<Employee> list = attribute.getList();
		for(Employee employee: list) {
			System.out.println("ID：" + employee.getdId()+ "===>Name：" + employee.getEmpName());
		}
		
		
	}
	
}
