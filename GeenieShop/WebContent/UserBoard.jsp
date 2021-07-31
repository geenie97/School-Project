<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ page import="java.io.PrintWriter"%>
 <%@ page import ="java.util.*" %>
 
<%@ page import="com.daniel.model.Board" %>
<%@ page import ="com.daniel.dao.BoardDao" %>

<%
	
	
	BoardDao dao = new BoardDao();
    List <Board> vec = dao.getAllBoards();
	
	
%>


    
<!DOCTYPE html>


<html>
	<head>
		<title>A-live shop -게시판</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css?after" />
		<%
		String USERID = (String) request.getSession().getAttribute("id");
	%>
	</head>
	<body class="left-sidebar is-preload">
		<div id="page-wrapper">

	<!-- Header -->
				<section id="header">
					<div class="container">

						<!-- Logo -->
							<h1 id="logo"><a href="index.jsp">A-Live Shop</a></h1>
							<p> 너굴이의 리빙 제품 샵입니다.</p>

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
			<!-- Main -->
			<section id="main">
			 	<div class="container" style="width: 1000px">

				<table 
					style="text-align: center; border: 1px solid #dddddd"
					id="boardList">



					<tr>

						<th
							style="background-color: #eeeeee; text-align: center; width: 50px;">번호</th>

						<th
							style="background-color: #eeeeee; text-align: center; width: 200px">게시글
							제목</th>

						<th
							style="background-color: #eeeeee; text-align: center; width: 80px">작성자</th>

						<th
							style="background-color: #eeeeee; text-align: center; width: 80px">작성 시간</th>
					

					</tr>
				
					<%
						for(int i=0; i<vec.size();i++){
							
							Board b =vec.get(i);
							int num =b.getNum();
							String title=b.getTitle();
							String name =b.getName();
							String date=b.getDate();
						
					%>
					<tr>
					<td><%= num %></td>
					<td>
					<a href="javascript: read('<%=num %>')">
						<%=title %>
					</a>
					</td>
					
					<td>
					<%=name %>
					</td>
					
					<td>
						<%=date %>
					</td>
					</tr>
					<%
					}
					%>

				</table>
				<div style="text-align: center;">
				<input type="button" onclick="gowrite()" class="button"
							value="글쓰기" />
				</div>
				</div>
				</section>
				</div>
				
		<script>

		function gowrite() {
			location.href="UserWriteBoard.jsp";
		}
		
		function read(num){
			location.href="UserShowBoard.jsp?num="+num;
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