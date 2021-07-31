<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
	
<%@ page import="java.io.PrintWriter"%>
 <%@ page import ="java.util.*" %>
	
<%@ page import="com.daniel.model.*" %>
<%@ page import ="com.daniel.dao.*" %>




<%
	
	UserDao uDao = new UserDao();	
	List <User> uvec =uDao.getAllUsers();
	
	BoardDao bDao= new BoardDao();
	List <Board> bvec=bDao.getAllBoards();
	
	ItemDao iDao =new ItemDao();
	List <Item> ivec = iDao.getAllItems();


%>

<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
<title>admin</title>
</head>
<body class="no-sidebar is-preload">
		<div id="page-wrapper">

		<section id="main">
			<h2 style="text-align:center" ><strong>Admin page</strong></h2>
			<div class="col-4 col-6-medium col-12-small">
				<h3>회원 관리</h3>
				<table id="usertable" style ="border:1px solid #dddddd">
					<tr>
					<td style="background-color: #eeeeee">ID</td>
					<td style="background-color: #eeeeee">이름</td>
					<td style="background-color: #eeeeee">주소</td>
					<td style="background-color: #eeeeee">핸드폰번호</td>
					<td style="background-color: #eeeeee">이메일</td>
					<td style="background-color: #eeeeee">관리</td>
					</tr>
						<%
						for(int i=0; i<uvec.size();i++){
							User u =uvec.get(i);
							String ID =u.getUserId();
							String Name=u.getName();
							String Address= u.getAddress();
							String phone =u.getPhone();
							String email =u.getEmail();
							
					%>
					<tr>
					<td><%= ID %></td>
					<td>
					<%=Name %>
					</td>					
					<td>
					<%=Address %>
					</td>
					<td>
						<%=phone %>
					</td>
					<td>
						<%=email %>
					</td>
					<td>
					<input type ="button" onclick="del_user('<%=ID %>')" value="삭제"
						class="button icon solid fa-file">
					</td>
					</tr>
					<%
					}
					%>
							
				</table>
				</div>
	<!--##########-->			
	<!-- 게시글 관리 -->			
				<div class="col-4 col-6-medium col-12-small">
					<h3>게시글 관리</h3>
			

				<table id="boardtable" style ="border:1px solid #dddddd">
					<tr>
						<td style="background-color: #eeeeee">게시물 번호</td>
						<td style="background-color: #eeeeee">작성자</td>
						<td style="background-color: #eeeeee">제목</td>
						<td style="background-color: #eeeeee">게시 날짜</td>
						<td style="background-color: #eeeeee">관리</td>
				
					<%
						for(int i=0; i<bvec.size();i++){
							
							Board b =bvec.get(i);
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
					<td>
					<input type ="button" onclick="del_board('<%=num %>')" value="삭제"
						class="button icon solid fa-file">
					</td>
					</tr>
					<%
					}
					%>

				</table>
				
				</div>
	<!--##########-->			
	<!-- 상품 관리 -->
				<div class="col-4 col-6-medium col-12-small">
					<h3>상품 관리 <input type ="button" onclick="add_Item()" value="상품추가"
						class="button icon solid fa-file">
					</h3>
					<table id="Itemtable" style ="border:1px solid #dddddd">
					<tr>
						<td style="background-color: #eeeeee">상품명</td>
						<td style="background-color: #eeeeee">상품가격</td>
						<td style="background-color: #eeeeee">상품수량</td>
						<td style="background-color: #eeeeee">관리</td>
					</tr>
					
						<%
						for(int i=0; i<ivec.size();i++){
							
							Item item =ivec.get(i);
							int itemID =item.getItemID();
							String title =item.getItemName();
							int count =Integer.parseInt(item.getCount());
						
					%>
					<tr>
					<td> <%= itemID %></td>
					<td>
						<%=title %>
					</td>
					
					<td>
						<%=count %>
					</td>
					<td>
					<input type ="button" onclick="del_Item('<%=itemID %>')" value="삭제"
						class="button icon solid fa-file">
					</td>
					</tr>
					<%
					}
					%>
					
					
					</table>
					
			<script>
				function del_user(id){
					alert("유저 정보를 삭제하시겠습니까?");
		            document.body.innerHTML+='<form id="myform" action="./LoginServlet" method="GET"><input name="id" value="'+id+'" /></form>';
		            document.getElementById('myform').submit();   
				}
				
				function del_board(num){
					alert("게시물을 삭제하시겠습니까?");
		            document.body.innerHTML+='<form id="myf" action="./BoardServlet" method="GET"><input name="num" value="'+num+'" /></form>';
		            document.getElementById('myf').submit();   
				}
				
				function del_Item(itemID){
					alert("상품을 삭제하시겠습니까?");
		            document.body.innerHTML+='<form id="form1" action="./ItemServLet" method="GET"><input name="itemID" value="'+itemID+'" /></form>';
		            document.getElementById('form1').submit(); 
				}
				
				function add_Item(){
					location.href="adminNewItem.jsp";
				}
			</script>
				
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
