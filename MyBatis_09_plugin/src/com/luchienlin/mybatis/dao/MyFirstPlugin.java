package com.luchienlin.mybatis.dao;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * 完成插件簽名：
 * 		告訴Mybatis當前插件用來攔截哪個對象哪個方法
 * @author Thomas Lu
 *
 */
@Intercepts(
		{
			@Signature(type=StatementHandler.class,method="parameterize",args=java.sql.Statement.class)
		})
public class MyFirstPlugin implements Interceptor {

	/**
	 * intercept：攔截：
	 * 	攔截目標對象目標方法的執行
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		System.out.println("第一MyFirstPlugin...intercept："+invocation.getMethod());
		// 動態的改變一下sql運行的參數：以前1號員工，實際從數據庫查詢3號員工
		System.out.println("當前攔截到的對象："+invocation.getTarget());
		// 拿到：StatementHandler==>ParameterHandler==>parameterObject
		// 拿到目標對象的原數據
		MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
		Object value = metaObject.getValue("parameterHandler.parameterObject");
		System.out.println("sql用的參數式："+value);
		// 修改完sql用的參數
		// 執行目標方法
		metaObject.setValue("parameterHandler.parameterObject", 11);
		Object proceed = invocation.proceed();
		
		// 返回值行後的返回值
		return proceed;
	}
	
	/**
	 * plugin：
	 * 		包裝目標對象的：包裝：為目標對象創建一個代理
	 */
	@Override
	public Object plugin(Object target) {
		
		System.out.println("第一MyFirstPlugin...plugin：mybatis將要包裝的對象："+target);
		// 我們可以借助Plugin的wrap方法來使當前Interceptor包裝我們目標對象
		Object wrap = Plugin.wrap(target, this);
		// 返回當前target創建的動態代理 
		return wrap;
	}
	
	/**
	 * setProperties：
	 * 		將插件註冊的property屬性設置過來
	 */
	@Override
	public void setProperties(Properties properties) {
		System.out.println("第一插件配置的訊息："+properties);
	}

}
