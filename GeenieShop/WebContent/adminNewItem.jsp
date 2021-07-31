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

		상품명: <input type="text" name="ItemName" id="ItemName"><br>
		파일 : <input type="file" name="file"><br>
		가격 :<input type="text" name="price" ><br>
		상세 정보:<input type="text" name="context"><br>
		상품 수량:<input type="text" name="count"><br>
  	

		<input type="submit" value="업로드"><br>

	</form>

</body>
</html>