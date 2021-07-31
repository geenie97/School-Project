<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>
<%@ page import="java.io.PrintWriter"%>
 <%@ page import ="java.util.*" %>
 
<%@ page import="com.daniel.model.*" %>
<%@ page import ="com.daniel.dao.*" %>
<%
	ItemDao Dao =new ItemDao();
	List <Item> vec = Dao.getAllItems();
	%>

  
<!DOCTYPE html>
<html>
	<head>
		<title>A-Live Shop</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css?after" />
		
		
	</head>
	
	<%
	String USERID = (String) request.getSession().getAttribute("id");
	String USERNAME=(String) request.getSession().getAttribute("name");
	%>
   
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


			<!-- 상품 페이지 업로드하기 -->
				<section id="features">
					<div class="container">
						<header>
							<h2><strong>상품 페이지</strong></h2>
						</header>
						
						<div class="row aln-center">
							<%
								for(int i=0; i<vec.size();i++){
									Item item =vec.get(i);
									String dir =item.getFile();
									String title=item.getItemName();
									String price=item.getPrice();
									int count=Integer.parseInt(item.getCount());
									dir ="images/"+dir;
									%>
									<div class="col-4 col-6-medium col-12-small" >
										<!-- Feature -->
											<section>
												<a href=# class="image featured">
												<img src="<%=dir %>" alt="" width=400 height=300 /></a>
												<header>
													<h3><%=title %>
													
													<input type ="button" onclick="go_detail(<%=item.getItemID() %>)" value="상세보기"
														class="button icon solid fa-file"> </h3> 
												</header>
												<%if(count==0){ %>
												<p style ="color:red">품절입니다</p>
												<%}else{ %>
												<p><%=price %></p>
												<%} %>
												
											</section>
											
									</div>
										<%}%>

									
							
							</div>
					</div>
				</section>
	</div>
	<script>
		function go_detail(itemID){
			location.href="UserShowDetail.jsp?itemID="+itemID;
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