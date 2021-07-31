<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ page import="java.io.PrintWriter"%>
    
<!DOCTYPE html>

<html>
	<head>
		<title>A-Live shop 게시글 입력하기</title>
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
			<div class="container">


				<div style="width: 1000px; margin: 0 auto;">
					<form method="post" name="addBoard" action=BoardAction.jsp>
						<table style="text-align: center; border: 1px solid #dddddd">
							<thead>
								<tr>
									<th colspan="2" text-align: center;">게시판 글쓰기</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><input type="text" id="title" class="form-control"
										placeholder="글 제목" name="title" maxlength="50" /></td>
								</tr>
								<tr>
									<td><textarea id="contents" class="form-control"
											placeholder="글 내용" name="content" maxlength="2048"
											style="height: 350px;"></textarea></td>
								</tr>
							</tbody>
						</table>
						<input type="button" onclick="register()" class="button alt"
							value="글쓰기" />
						<input type="button" onclick="goback()" class="button alt"
							value="돌아가기" />
					</form>
					<script>
					function goback(){
						history.back();
					}
					
					function register(){
						var title = document.getElementById("title").value;
						var contents = document.getElementById("contents").value;
						
						if(title==""){
							alert("글 제목을 입력해 주세요!");
							return;
						}
						if(contents=""){
							alert("글 내용을 입력해주세요!");
							return;
						}
						
						document.addBoard.submit();
					}
					
					
					</script>

					</div>
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