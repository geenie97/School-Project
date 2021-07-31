<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>
    
    

<%@ page import="java.io.PrintWriter"%>

<!DOCTYPE html>


<html>
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
		  
<title>Insert title here</title>
</head>
<body class="left-sidebar is-preload">
	<script>
	
	function check_pw(){
		
		var pw = document.getElementById("userPassword").value;
		var check = document.getElementById("checkpwd").value;
		
	
		alert("hihi");	
		
		if(check != pw){
			prompt("비밀번호 일치 확인을 해주세요");			
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
							<strong>Sgin-up</strong>
						</h2>
					</header>
					
					<form action ="./SignUpAction.jsp" id="form" method="POST">

						<header>
							<h3>User ID</h3>
							<div class="row gtr-50">
							<div class="col-6 col-12-small">
							<input type="text" id="userID" name="userID"
								class="form-control" />
							</div>
			
							<div class="col-6 col-12-small">
							<input type="button" onclick="mycheck()" id="check" value="중복확인"
								class="button icon solid fa-file">
							</div>
							</div>
						</header>
						<header>
							<h3>Password</h3>
							<input type="password" id="userPassword" name="pwd"
								class="form-control" />
						</header>

						<header>
							<h3>Password 확인하기</h3>
							<input type="password" id="checkpw" name="checkpw"
								class="form-control" />
						</header>

						<header>
							<h3>Name</h3>
							<input type="text" name="userName" class="form-control" />
						</header>

						<header>
							<h3>Address</h3>
							<input type="text" name="userAddress" class="form-control" />
						</header>

						<header>
							<h3>Phone Number</h3>
							<input type="text" name="userPhone" class="form-control" />
						</header>

						<header>
							<h3>Email</h3>
							<input type="text" name="userEmail" class="form-control" />
						</header>


					<br> <input type ="submit" value="회원가입"
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