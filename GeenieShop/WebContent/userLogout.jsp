<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
session.invalidate();
System.out.println("로그아웃성공");
%>
<script type="text/javascript">
	alert("정상적으로 로그아웃 되었습니다");
	location.href="index.jsp";
</script>
</body>
</html>