package com.daniel.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daniel.dao.ItemDao;
import com.daniel.dao.CartDao;
import com.daniel.model.Board;
import com.daniel.model.Cart;
import com.daniel.model.Item;


@WebServlet("/CartServLet")
public class CartController extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	   
	    private CartDao dao;
	    private ItemDao idao;

	    public CartController() {
	        super();
	        dao = new CartDao();
	        idao= new ItemDao();
	    }

	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	Item item=new Item();
	    	String num = request.getParameter("num");
	    	System.out.println("get들어옴"+num);
	    	
	    	Cart cart=dao.getcartBynum(num);
	    	String itemID = cart.getItemID();
	    	
	    	item =idao.getItemById(itemID);
	    	int cnt =Integer.parseInt(item.getCount())+1;
	    	item.setCount(Integer.toString(cnt));
	    	idao.updateItem(item);
	    	
	    	
	        dao.deleteCart(num);

	        String succes = "UserCart.jsp";
			response.sendRedirect(succes);
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	Item item=new Item();
	    	Cart cart =new Cart();
	    	
	    	
	    	String itemID =request.getParameter("itemID");
	    	item =idao.getItemById(itemID);
	    	int cnt =Integer.parseInt(item.getCount())-1;
	    	item.setCount(Integer.toString(cnt));
	    	System.out.println("post들어옴"+itemID);
	    	
	    	idao.updateItem(item);
	    	
	    	String userID= (String) request.getSession().getAttribute("id");
	    	String title =item.getItemName();
	    	String price =item.getPrice();
	    	
	    	cart.setItemID(itemID);
	    	cart.setUserID(userID);
	    	cart.setTitle(title);
	    	cart.setPrice(price);
	    	
	    	dao.addCart(cart);
	    	String succes = "UserCart.jsp";
    		response.sendRedirect(succes);
	    	
	      
	    }
	        
	    
}