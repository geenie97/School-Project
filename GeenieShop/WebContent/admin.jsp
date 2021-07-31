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
				<h3>ȸ�� ����</h3>
				<table id="usertable" style ="border:1px solid #dddddd">
					<tr>
					<td style="background-color: #eeeeee">ID</td>
					<td style="background-color: #eeeeee">�̸�</td>
					<td style="background-color: #eeeeee">�ּ�</td>
					<td style="background-color: #eeeeee">�ڵ�����ȣ</td>
					<td style="background-color: #eeeeee">�̸���</td>
					<td style="background-color: #eeeeee">����</td>
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
					<input type ="button" onclick="del_user('<%=ID %>')" value="����"
						class="button icon solid fa-file">
					</td>
					</tr>
					<%
					}
					%>
							
				</table>
				</div>
	<!--##########-->			
	<!-- �Խñ� ���� -->			
				<div class="col-4 col-6-medium col-12-small">
					<h3>�Խñ� ����</h3>
			

				<table id="boardtable" style ="border:1px solid #dddddd">
					<tr>
						<td style="background-color: #eeeeee">�Խù� ��ȣ</td>
						<td style="background-color: #eeeeee">�ۼ���</td>
						<td style="background-color: #eeeeee">����</td>
						<td style="background-color: #eeeeee">�Խ� ��¥</td>
						<td style="background-color: #eeeeee">����</td>
				
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
					<input type ="button" onclick="del_board('<%=num %>')" value="����"
						class="button icon solid fa-file">
					</td>
					</tr>
					<%
					}
					%>

				</table>
				
				</div>
	<!--##########-->			
	<!-- ��ǰ ���� -->
				<div class="col-4 col-6-medium col-12-small">
					<h3>��ǰ ���� <input type ="button" onclick="add_Item()" value="��ǰ�߰�"
						class="button icon solid fa-file">
					</h3>
					<table id="Itemtable" style ="border:1px solid #dddddd">
					<tr>
						<td style="background-color: #eeeeee">��ǰ��</td>
						<td style="background-color: #eeeeee">��ǰ����</td>
						<td style="background-color: #eeeeee">��ǰ����</td>
						<td style="background-color: #eeeeee">����</td>
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
					<input type ="button" onclick="del_Item('<%=itemID %>')" value="����"
						class="button icon solid fa-file">
					</td>
					</tr>
					<%
					}
					%>
					
					
					</table>
					
			<script>
				function del_user(id){
					alert("���� ������ �����Ͻðڽ��ϱ�?");
		            document.body.innerHTML+='<form id="myform" action="./LoginServlet" method="GET"><input name="id" value="'+id+'" /></form>';
		            document.getElementById('myform').submit();   
				}
				
				function del_board(num){
					alert("�Խù��� �����Ͻðڽ��ϱ�?");
		            document.body.innerHTML+='<form id="myf" action="./BoardServlet" method="GET"><input name="num" value="'+num+'" /></form>';
		            document.getElementById('myf').submit();   
				}
				
				function del_Item(itemID){
					alert("��ǰ�� �����Ͻðڽ��ϱ�?");
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
