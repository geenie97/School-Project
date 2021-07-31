package com.daniel.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daniel.dao.BoardDao;
import com.daniel.dao.ItemDao;
import com.daniel.model.Item;


@WebServlet("/BoardServlet")
public class BoardController extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	   
	    private BoardDao dao;

	    public BoardController() {
	        super();
	        dao = new BoardDao();
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    	String userId = request.getParameter("num");
	    	System.out.println("get들어옴"+userId);
	      
	    	dao.deleteBoard(userId);
	        String succes = "admin.jsp";
			response.sendRedirect(succes);
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       
	           
	      
	    }
	        
	    
}
