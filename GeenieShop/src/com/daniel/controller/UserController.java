package com.daniel.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.daniel.dao.UserDao;
import com.daniel.model.User;


@WebServlet("/LoginServlet")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
   
    private UserDao dao;

    public UserController() {
        super();
        dao = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String userId = request.getParameter("id");
    	System.out.println("get들어옴"+userId);
        dao.deleteUser(userId);

        String succes = "admin.jsp";
		response.sendRedirect(succes);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	User user = new User();
   

        user.setName(request.getParameter("Name"));     
        user.setAddress(request.getParameter("address"));
        user.setPhone(request.getParameter("phone"));
        user.setEmail(request.getParameter("email"));
        String userid = request.getParameter("userid");
        if(userid == null || userid.isEmpty())
        {
            dao.addUser(user);
        }
        else
        {
            user.setUserId(userid);
            dao.updateUser(user);
        }
        
    }
}