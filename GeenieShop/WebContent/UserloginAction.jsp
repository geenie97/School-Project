<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>

<%@ page import="com.daniel.model.User" %>
<%@ page import ="com.daniel.dao.UserDao" %>

<%@ page import="java.io.PrintWriter" %>


<%

 request.setCharacterEncoding("euc-kr"); 




	String userID = null;
	String userPW = null;
	String userName=null;
	String userAddress =null;
	String phone=null;
	String email =null;
	

	

	// �Է��� �Ǿ��ٸ� ������ �����Ͱ��� �־���
	if(request.getParameter("userId") != ""){
		userID = (String)request.getParameter("userId");
		}

	if(request.getParameter("pwd") != ""){
		userPW = (String)request.getParameter("pwd");	
		}
	
	
	User user =new User();
	

	
	if(userID == null || userPW == null ){

		// �ڹٽ�ũ��Ʈ �������� �Է� �ȵ� �κ��� ������ �˶��� ����

		// history.back() ���� �� �������� �̵��ϰ� �Ѵ�

		PrintWriter script = response.getWriter();

		script.println("<script>");

		script.println("alert('�Է��� �ȵ� �κ��� �ֽ��ϴ�');");

		script.println("history.back();");

		script.println("</script>");

		script.close();

		return;

	}
	else{
		
		user.setUserId(userID);
		user.setPassword(userPW);
	}

	// ���� �Է� �Ǿ��ٸ� ��� �־����� 1�̸� ����, -1�̸� �����߻�
	
	
	
	  
	
	UserDao userDao = new UserDao();
	//

	String result =userDao.userLogin(userID,userPW);

	
	
	
	// ȸ������ �� �˸�â�� ��� �� index.jsp�� �̵�

	PrintWriter script = response.getWriter();

	if(result =="No"){
		script.println("<script>alert('�α��� ����!'); location.href='./index.jsp';</script>");

	}
	else{
		script.println("<script>alert('�α��� ����! ȯ���մϴ�');location.href='./index.jsp';</script>");
		session.setAttribute("id",userID);
		session.setAttribute("name",result);
	}	
	
	
	script .flush();

%>