<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>
<%@ page import="java.io.PrintWriter"%>
 <%@ page import ="java.util.*" %>
 
<%@ page import="com.daniel.model.*" %>
<%@ page import ="com.daniel.dao.*" %>

	
<%
	String USERID = (String) request.getSession().getAttribute("id");
	String USERNAME=(String) request.getSession().getAttribute("name");
	String id = (String) request.getParameter("itemID");
	%>
   
<%
	ItemDao Dao =new ItemDao();
	Item item =new Item();
	item=Dao.getItemById(id);
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
		<!-- 상품 디테일 페이지 -->		
				<section id="main">
			 	<div class="container" style="width: 1000px">

				<table style="text-align: center; border: 2px solid #dddddd"
					id="boardList">
					<tr>
					<td style="background-color: #eeeeee" colspan ="4"> 상품명:<%=item.getItemName() %> 
					</tr>
					<tr style="background-color: #eeeeee">
					<td style="border: 1px solid #dddddd"> 가격:<%=item.getPrice() %></td>
					<td style="border: 1px solid #dddddd"> 
						<input type="button" onclick="go_cart(<%=id %>)" value="장바구니 담기"
						class="button icon solid fa-file"></td>
					</tr>
					<tr style="background-color: #eeeeee">
					<td colspan="4">
					상세설명:
					<%=item.getContext()%></td>
					</tr>
					<tr>
					<td colspan="4">
					
					<a href=# class="image featured">
					<img src="images/<%=item.getFile() %>" alt=""  /></a>
					
					
					
					</td>
					</tr>
					</table>
					<br>
					<input type="button" onclick="goback()" value="되돌아가기"
						class="button icon solid fa-file">
						
					</div>
					</section>
				
				</div>
			
	<script>
		function go_detail(itemID){
			location.href="UserShowDetail.jsp?itemID="+itemID;
		}
		
		function go_cart(itemID){
			alert("장바구니에 저장하겠습니다!");
			document.body.innerHTML+='<form id="form" action="./CartServLet" method="POST"><input name="itemID" value="'+itemID+'" /></form>';
		    document.getElementById('form').submit(); 
		    }
		
		
		function goback() {
			location.href="UserShowItems.jsp"
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