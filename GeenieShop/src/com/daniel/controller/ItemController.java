package com.daniel.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

import com.daniel.dao.ItemDao;
import com.daniel.model.Item;


@WebServlet("/ItemServLet")
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    private ItemDao dao;

    public ItemController() {
        super();
        dao = new ItemDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String Id = request.getParameter("itemID");
    	System.out.println("get들어옴"+Id);
        dao.deleteItem(Id);

        String succes = "admin.jsp";
		response.sendRedirect(succes);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		 
    		Item item  = new Item();
    		String uploadDir=this.getClass().getResource("").getPath();
    		uploadDir ="C:\\Users\\SAMSUNG\\Documents\\SimpleJspServletDB-master\\SimpleJspServletDB-master\\SimpleJspServletDB\\WebContent\\images";
    		System.out.println("절대경로: "+uploadDir+"<br/>");
    		
    		int maxSize=1024*1024*100;
    		String encoding= "euc-kr";
    		
    		System.out.println("어디까지되나");
    		MultipartRequest mr =new MultipartRequest(request,uploadDir,maxSize,encoding,
    				new DefaultFileRenamePolicy());
    		
    		System.out.println("어디까지되나2");
    		String itemName=mr.getParameter("ItemName");
    		String file =mr.getFilesystemName("file");
    		String context=mr.getParameter("context");
    		String price = mr.getParameter("price");
    		String count = mr.getParameter("count");
    		
    		System.out.print(itemName +" "+ file+" "+context+" "+price+" "+count);
    		
    		item.setCount(count);
    		item.setContext(context);
    		item.setFile(file);
    		item.setItemName(itemName);
    		item.setPrice(price);
    		
    		int result =0;
    		result =dao.upload(item);
    		System.out.println("디비에 업로드는 왜 안하는지");
    		
    		
    		if(result == -3) {System.out.println("connection error");}
    		if(result == -1) {System.out.println("input error");}
    		String succes = "admin.jsp";
    		response.sendRedirect(succes);
    		
    }	
        
    
}

