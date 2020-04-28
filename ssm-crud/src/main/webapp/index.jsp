<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>員工列表</title>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- web路徑：
不以/開始相對路徑，找資源，以當前資源的路徑為基準，經常容易出問題。
以/開始的相對路徑，找資源，以服務氣的路徑為標準(http://localhost:3306/crud)：需要加上項目名
				http://localhost:3306/crud
 -->
<link rel="stylesheet" href="${APP_PATH }/static/bootstrap-4.4.1-dist/css/bootstrap.min.css">
<script src="${APP_PATH }/static/js/jquery-3.5.0.js"></script>
<script src="${APP_PATH }/static/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/c3dc04dc4d.js" crossorigin="anonymous"></script>
</head>
<body>
	<!-- 搭建顯示頁面 -->
	<div class="container">
		<!-- 標題 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按鈕 -->
		<div class="row mb-1">
			<div class="col-md-4 offset-md-8">
				<button class="btn btn-primary btn-sm">新增</button>
				<button class="btn btn-danger btn-sm">刪除</button>
			</div>
		</div>
		<!-- 顯示表格數據 -->
		<div class="row mb-1">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">empName</th>
							<th scope="col">gender</th>
							<th scope="col">email</th>
							<th scope="col">deptName</th>
							<th scope="col">操作</th>
						</tr>
					</thead>
					<tbody>
					
					
					
					</tbody>
				</table>
			</div>
		</div>
		<!-- 顯示分頁數據 -->
		<div class="row">
			<!-- 分頁文字訊息 -->
			<div class=""></div>
			<!-- 分頁條訊息 -->
			<div class="col-md-6">
				當前頁碼為第頁，共筆資料
			</div>
			<div class="col-md-6">
			</div>
		</div>
	</div>
	<script type="text/javascript">
		// 1. 頁面加載數據以後，直接運行一個ajax請求，要到分頁數據
		$(function(){
			$.ajax({
				url:"${APP_PATH}/emps", // URI請求路徑
				data:"pn=1", // 請求要到第幾頁
				type:"GET", // GET POST都行
				success:function(result){
					// console.log(result);
					// 1. 解析並顯示員工數據
					build_emps_table(result);
					// 2. 解析並顯示分頁訊息
				}
			});	
		});
		
		// 解析：
		function build_emps_table(result){
			// 所有的員工數據
			var emps = result.extend.pageInfo.list;
			// 遍歷這個陣列，第一個參數代表索引，第二個代表當前的對象
			$.each(emps, function(index, item){
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(item.gender=='M'?"男":"女");
				var emailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>").append(item.department);
				var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
								.append($("<i></i>").addClass("fas fa-pencil-ruler")).append("編輯");
				var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
								.append($("<i></i>").addClass("fas fa-trash-alt")).append("刪除");
				// 添加到一格內
				var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
				$("<tr></tr>").append(empIdTd)
					.append(empNameTd)
					.append(genderTd)
					.append(emailTd)
					.append(deptNameTd)
					.append(btnTd)
					.appendTo("#emps_table tbody"); // 添加到哪裡去
				
			})
		}
		
		function build_page_nav(result){
			
		}
		
	</script>
</body>
</html>