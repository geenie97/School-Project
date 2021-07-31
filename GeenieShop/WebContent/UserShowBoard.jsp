<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ page import="com.daniel.model.Board" %>
<%@ page import ="com.daniel.dao.BoardDao" %>

 <%@ page import ="java.util.*" %>
<%@ page import="java.io.PrintWriter" %>

<%request.setCharacterEncoding("utf-8");%>
<%
	String USERID = (String) request.getSession().getAttribute("id");
	String USERNAME=(String) request.getSession().getAttribute("name");
	String num = (String) request.getParameter("num");
%>
<%
	BoardDao dao = new BoardDao();
	Board b = new Board();
	
	b =dao.getBoardById(num);
%>



<jsp:useBean id="board1" class="com.daniel.model.Board">
	<jsp:setProperty name="board1" property="*"/>
	<jsp:setProperty name="board1" property="name" value="<%=USERNAME %>"/>
	
</jsp:useBean>

<!DOCTYPE html>
<html>
	<head>
		<title>A-Live Shop</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css?after" />
		
		
	</head>
	<script>
		
	
		
		function goback() {
			location.href="UserBoard.jsp"
		}
	</script>
	
	
	<body class="homepage is-preload">
		<div id="page-wrapper">

			<!-- Header -->
				<section id="header">
					<div class="container">

						<!-- Logo -->
							<h1 id="logo"><a href="index.jsp">A-Live Shop</a></h1>
							<p>너굴이의 리빙 제품 샵입니다.</p>

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
			<!--Main -->
				<section id="main">
			 	<div class="container" style="width: 1000px">

				<table style="text-align: center; border: 2px solid #dddddd"
					id="boardList">
					<tr>
					<td style="background-color: #eeeeee" colspan ="4"> 글제목: <%= b.getTitle() %>
					</tr>
					<tr style="background-color: #eeeeee">
					<td style="border: 1px solid #dddddd"> 작성자: <%=b.getName() %></td>
					<td style="border: 1px solid #dddddd"> 작성 날짜: <%=b.getDate() %></td>
					</tr>
					<tr style="background-color: #eeeeee">
					<td colspan="4">작성 내용:</td>
					</tr>
					<tr>
					<td colspan="4">
					<%
						String content =b.getContent();
						content=content.replace("\n","<br>");
					%>
					<%= content %>
					</td>
					</tr>
					</table>
					<br>
					<input type="button" onclick="goback()" value="되돌아가기"
						class="button icon solid fa-file">
						
					</div>
					</section>
				
				</div>
	<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.dropotron.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>

	</body>
</html>