<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
		  
<title>Insert title here</title>
</head>




<body class="left-sidebar is-preload">
<script>

function go_admin() {
	var id = document.getElementById("id").value;
	var pw = document.getElementById("pwd").value;

	if (id == "geenie") {
		if (pw == "1004") {
			alert("관리자님, 안녕하세요.");
			location.href = "admin.jsp";
		} else {
			alert("잘못된 비밀번호입니다");
		}
	} else {
		alert("잘못된 ID입니다.")
	}

  }
</script>

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
										<a href="#" class="icon fa-chart-bar"><span>Category</span></a>
										<ul>
											<li><a href="#">가구</a></li>
											<li><a href="#">수납/정리</a></li>											
											<li><a href="#">주방용품</a></li>
										</ul>
									</li>
									<li><a class="icon solid fa-cog" href="UserBoard.jsp"><span>OnBoard</span></a></li>
									<li><a class="icon solid fa-retweet" href="UserCart.jsp"><span>Cart</span></a></li>
									<li>
									<a href="#" class="icon solid fa-sitemap"><span>Log-in</span></a>
										<ul>
											<li><a href="userLogin.jsp">log - in</a></li>
											<li><a href="SignUp.jsp">Signup</a></li>
											<li><a href="#">admin</a></li>											
										</ul>
									</li>
								</ul>
							</nav>

					</div>
				</section>
				
<!-- Main -->
		<section id="main">
			<div class="container" style="max-width: 560px;">

				<!-- Content -->
				<article class="box post">

					<header style="text-align: center;">
						<h2>
							<strong>#Admin 전용 창입니다#</strong>
						</h2>
					</header>
					
					
					<form id="form1" method="POST" action="UserloginAction.jsp">

					<header>
						<h3>Admin ID</h3>
							<input type="text" id="id" name="userId" placeholder="ID" class="icon solid fa-home" />
						</header>

						<header>
							<h3>Password</h3>
							
							<input type="password" id="pwd" name="pwd" placeholder ="Password"class="icon solid fa-home" /><br>
						</header>

						<br>
						 <input type="button" onclick="go_admin()" value="로그인"
						class="button icon solid fa-file">
						
						
					</form>
				

				</article>

			</div>
		</section>
					

				
</div>
				
		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.dropotron.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
</body>
</html>