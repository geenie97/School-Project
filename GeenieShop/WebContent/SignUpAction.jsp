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
	if(request.getParameter("userID") != null){
		userID = (String)request.getParameter("userID");
		}

	if(request.getParameter("pwd") != null){
		userPW = (String)request.getParameter("pwd");	
		}
	if(request.getParameter("userName") != null){
		userName = (String)request.getParameter("userName");
	}
	if(request.getParameter("userAddress") != null){
		userAddress = (String)request.getParameter("userAddress");
	}
	if(request.getParameter("userPhone") != null){
		phone = (String)request.getParameter("userPhone");
	}
	if(request.getParameter("userEmail") != null){
		email = (String)request.getParameter("userEmail");
	}

	
	User user =new User();
	

	
	if(userID == null || userPW == null || userName==null ||userAddress==null
			|| phone ==null || email ==null){

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
		user.setName(userName);
		user.setAddress(userAddress);
		user.setPhone(phone);
		user.setEmail(email);
	}

	// ���� �Է� �Ǿ��ٸ� ��� �־����� 1�̸� ����, -1�̸� �����߻�
	
	
	
	  
	
	UserDao userDao = new UserDao();
	//

	int result =userDao.addUser(user);

	

	// ȸ������ �� �˸�â�� ��� �� index.jsp�� �̵�

	PrintWriter script = response.getWriter();

	
	//if(result ==-3) {script .println("<script>alert('���� ����!'); location.href='./index.jsp';</script>");}
	script .println("<script>alert('������ ��� �Ǿ����ϴ�'); location.href='./index.jsp';</script>");

	script .flush();

%>