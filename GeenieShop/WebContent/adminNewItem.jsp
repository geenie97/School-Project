<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<title>Insert title here</title>
</head>
<body>


	
	
	<form action="./ItemServLet" method="post" enctype="multipart/form-data" accept-charest="euc-kr">

		��ǰ��: <input type="text" name="ItemName" id="ItemName"><br>
		���� : <input type="file" name="file"><br>
		���� :<input type="text" name="price" ><br>
		�� ����:<input type="text" name="context"><br>
		��ǰ ����:<input type="text" name="count"><br>
  	

		<input type="submit" value="���ε�"><br>

	</form>

</body>
</html>