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
				<table class="table table-hover">
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
						<c:forEach items="${pageInfo.list }" var="emp">
							<tr>	
							<th scope="row">${emp.empId }</th>
							<th>${emp.empName }</th>
							<th>${emp.gender=="M"?"男":"女" }</th>
							<th>${emp.email }</th>
							<th>${emp.department.deptName }</th>
							<th>
								<button class="btn btn-primary">
									<i class="fas fa-pencil-ruler"></i>編輯
								</button>
								<button class="btn btn-danger">
									<i class="fas fa-trash-alt"></i>刪除
								</button>
							</th>
							</tr>
						</c:forEach>
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
				當前紀錄數：xxx
			</div>
			<div class="col-md-6">
				<nav aria-label="Page navigation example">
					<ul class="pagination">
						<li class="page-item"><a class="page-link" href="#">首頁</a></li>
						<li class="page-item"><a class="page-link" href="#"
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
								<span class="sr-only">Previous</span>
						</a></li>
						
						<c:forEach items="${pageInfo.navigatepageNums }" var="page_Num">
							<c:if test="${pageInfo.pageNum == page_Num }">
								<li class="page-item active"><a class="page-link" href="#">${page_Num }</a></li>
							</c:if>
							<c:if test="${pageInfo.pageNum != page_Num }">
								<li class="page-item"><a class="page-link" href="${APP_PATH }/emps?pn=${page_Num}">${page_Num }</a></li>
							</c:if>
						</c:forEach>
						
						<li class="page-item"><a class="page-link" href="#"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
								class="sr-only">Next</span>
						</a></li>
						<li class="page-item"><a class="page-link" href="#">末頁</a></li>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>