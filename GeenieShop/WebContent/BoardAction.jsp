<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.daniel.model.Board" %>
<%@ page import ="com.daniel.dao.BoardDao" %>

<%@ page import="java.io.PrintWriter" %>

<%request.setCharacterEncoding("utf-8");%>
<%
	String USERID = (String) request.getSession().getAttribute("id");
	String USERNAME=(String) request.getSession().getAttribute("name");
%>

<jsp:useBean id="board1" class="com.daniel.model.Board">
	<jsp:setProperty name="board1" property="*"/>
	<jsp:setProperty name="board1" property="name" value="<%=USERNAME %>"/>
	
</jsp:useBean>


<%
	
	BoardDao dao=new BoardDao();
	int result =dao.addBoard(board1);
	
		PrintWriter script = response.getWriter();
	if(result==-3){
		script .println("<script>alert('연결실패'); </script>");
	}
	else if(result == -1){
		script .println("<script>alert('인서트실패'); </script>");
	}
	else{
		script .println("<script>alert('작성이 완료되었습니다!'); location.href='./UserBoard.jsp'</script>");
	}
		script .flush();

	
	
%>