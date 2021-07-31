<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.io.PrintWriter"%>
 <%@ page import ="java.util.*" %>
 <%
	String USERID = (String) request.getSession().getAttribute("id");
	String USERNAME=(String) request.getSession().getAttribute("name");
	%>
   
 
<%@ page import="com.daniel.model.*" %>
<%@ page import ="com.daniel.dao.*" %>
<%
	CartDao Dao =new CartDao();
	List <Cart> vec = Dao.getcartById(USERID);
	%>

  
  
<!DOCTYPE html>
<html>
	<head>
		<title>A-Live Shop</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css?after" />
		
		
	</head>
	
	
	<body class="homepage is-preload">
		<div id="page-wrapper">

			<!-- Header -->
				<section id="header">
					<div class="container">

						<!-- Logo -->
							<h1 id="logo"><a href="index.jsp">A-Live Shop</a></h1>
							<% if(USERID==null){
								%>
								<p>안녕하세요. 너굴이의 리빙 제품 샵입니다.</p>
								<%}else{ %>						
							<p><%=USERNAME %>님, 안녕하세요. 너굴이의 리빙 제품 샵입니다.</p>
							<%
								}
							%>
						<!-- Nav -->
							<nav id="nav">
								<ul>
									<li><a class="icon solid fa-home" href="index.jsp"><span>Main</span></a></li>
									<li>
										<a href="UserShowItems.jsp" class="icon fa-chart-bar"><span>Items</span></a>
									</li>
									<li><a class="icon solid fa-cog" href="UserBoard.jsp"><span>OnBoard</span></a></li>
									<li><a class="icon solid fa-retweet" href="UserCart.jsp"><span>Cart</span></a></li>
									<li>
									<a href="#" class="icon solid fa-sitemap"><span>Log-in</span></a>
										<ul><%
										if(USERID==null){
										%>
											<li><a href="userLogin.jsp">log - in</a></li>
											<li><a href="SignUp.jsp">Signup</a></li>
											<li><a href="adminLogin.jsp">admin</a></li>											
										<%
										}else{
										%>
											<li><a href="userLogout.jsp">Log-out</a></li>
											<%
											}
											%>
										</ul>
									</li>
								</ul>
							</nav>

					</div>
				</section>
	<!-- 카트 목록 -->
		<section id="main">
			<div class="container" style="width: 1000px">

				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd"
					id="boardList">



					<tr>

						<th
							style="background-color: #eeeeee; text-align: center; width: 50px;">번호</th>

						<th
							style="background-color: #eeeeee; text-align: center; width: 200px">상품명</th>

						<th
							style="background-color: #eeeeee; text-align: center; width: 80px">가격</th>
							
						<th
							style="background-color: #eeeeee; text-align: center; width: 80px">관리</th>
							

					

					</tr>
					<%
						for(int i=0; i<vec.size();i++){
							
							Cart cart =vec.get(i);
								
							String title =cart.getTitle();
							String price =cart.getPrice();
						
					%>
					<tr>
					<td> <%= i %></td>
					<td>
						<%=title %>
					</td>
					
					<td>
						<%=price %>
					</td>
					<td>
					<input type ="button" onclick="del_Cart('<%=cart.getNum() %>')" value="삭제"
						class="button icon solid fa-file">
					</td>
					</tr>
					<%
					}
					%>
					


				</table>
				
				<div style="text-align: center;">
				<input type="button" onclick="gocash()" class="button"
							value="주문하기" />
				</div>
				</div>
				</section>
				</div>
		<script>
		function del_Cart(id){
			alert("장바구니에서 삭제합니다");
            document.body.innerHTML+='<form id="myform" action="./CartServLet" method="GET"><input name="num" value="'+id+'" /></form>';
            document.getElementById('myform').submit();   
		}
		
		
		</script>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.dropotron.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>

	</body>
</html>