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
	

	

	// 입력이 되었다면 변수로 데이터값을 넣어줌
	if(request.getParameter("userId") != ""){
		userID = (String)request.getParameter("userId");
		}

	if(request.getParameter("pwd") != ""){
		userPW = (String)request.getParameter("pwd");	
		}
	
	
	User user =new User();
	

	
	if(userID == null || userPW == null ){

		// 자바스크립트 구문으로 입력 안된 부분이 있으면 알람을 띄우고

		// history.back() 통해 전 페이지로 이동하게 한다

		PrintWriter script = response.getWriter();

		script.println("<script>");

		script.println("alert('입력이 안된 부분이 있습니다');");

		script.println("history.back();");

		script.println("</script>");

		script.close();

		return;

	}
	else{
		
		user.setUserId(userID);
		user.setPassword(userPW);
	}

	// 정상 입력 되었다면 디비에 넣어주자 1이면 정상, -1이면 오류발생
	
	
	
	  
	
	UserDao userDao = new UserDao();
	//

	String result =userDao.userLogin(userID,userPW);

	
	
	
	// 회원가입 후 알림창을 띄운 후 index.jsp로 이동

	PrintWriter script = response.getWriter();

	if(result =="No"){
		script.println("<script>alert('로그인 실패!'); location.href='./index.jsp';</script>");

	}
	else{
		script.println("<script>alert('로그인 성공! 환영합니다');location.href='./index.jsp';</script>");
		session.setAttribute("id",userID);
		session.setAttribute("name",result);
	}	
	
	
	script .flush();

%>