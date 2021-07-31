package com.daniel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.daniel.model.Cart;
import com.daniel.model.Item;
import com.daniel.util.cartDbUtil;




public class CartDao {
	private Connection connection;

	public CartDao() {
		connection = cartDbUtil.getConnection();
	}
	
	public int addCart(Cart cart) {
		if(connection == null) return -3;
		try {

			PreparedStatement ptmst = connection
					.prepareStatement("insert into carts(userID,itemID,title,price) values (?, ?, ?, ? )");
			// Parameters start with 1
			ptmst.setString(1, cart.getUserID());
			ptmst.setString(2,cart.getItemID());
			ptmst.setString(3, cart.getTitle());
			ptmst.setString(4, cart.getPrice());
			
			
			ptmst.executeUpdate();
			//connection.close();
			//preparedStatement.close();
			return 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	public List<Cart> getcartById(String num) {
		List<Cart> carts = new ArrayList<Cart>();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from carts where userID=?");
			preparedStatement.setString(1, num);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				Cart cart = new Cart();
				cart.setNum(Integer.parseInt(rs.getString("num")));
				cart.setUserID(rs.getString("userID"));
				cart.setItemID(rs.getString("itemID"));
				cart.setTitle(rs.getString("title"));
				cart.setPrice(rs.getString("price"));
				carts.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return carts;
	}
	
	public Cart getcartBynum(String num) {
		Cart cart = new Cart();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from carts where num=?");
			preparedStatement.setString(1, num);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				cart.setNum(Integer.parseInt(rs.getString("num")));
				cart.setUserID(rs.getString("userID"));
				cart.setItemID(rs.getString("itemID"));
				cart.setTitle(rs.getString("title"));
				cart.setPrice(rs.getString("price"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cart;

	}


	public void deleteCart(String num) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from carts where num=?");
			// Parameters start with 1
			preparedStatement.setString(1, num);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}