package com.daniel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.daniel.model.Item;
import com.daniel.model.User;
import com.daniel.util.ItemDbUtil;


public class ItemDao {

	private Connection connection;

	public ItemDao() {
		//////////////////////dITemdb
		connection = ItemDbUtil.getConnection();
	}

	public int upload(Item item) {
		if(connection == null) return -3;
		try {
			
			PreparedStatement ptmst = connection
					.prepareStatement("insert into items(file,itemName,price,count,context) values (?, ?,?, ?, ? )");
			// Parameters start with 1
			ptmst.setString(1, item.getFile());
			ptmst.setString(2,item.getItemName());
			ptmst.setString(3, item.getPrice());
			ptmst.setString(4, item.getCount());
			ptmst.setString(5, item.getContext());
			
			ptmst.executeUpdate();
			//connection.close();
			//preparedStatement.close();
			return 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void deleteItem(String ID) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from items where itemId=?");
			// Parameters start with 1
			preparedStatement.setString(1, ID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	


	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<Item>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from items");
			while (rs.next()) {
				Item item = new Item();
				item.setItemID(Integer.parseInt(rs.getString("itemID")));
				item.setFile(rs.getString("file"));
				item.setItemName(rs.getString("itemName"));
				item.setPrice(rs.getString("price"));
				item.setCount(rs.getString("count"));
				item.setContext(rs.getString("context"));
				
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}
	
	public Item getItemById(String itemId) {
		Item item = new Item();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from items where itemID=?");
			preparedStatement.setString(1, itemId);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				item.setItemID(Integer.parseInt(rs.getString("itemID")));
				item.setFile(rs.getString("file"));
				item.setItemName(rs.getString("itemName"));
				item.setPrice(rs.getString("price"));
				item.setCount(rs.getString("count"));
				item.setContext(rs.getString("context"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return item;
	}
	
	public void updateItem(Item item) {
		try {
			PreparedStatement ptmst = connection
					.prepareStatement("update items set file=?, itemName=?,price=?,count=? ,context=?" +
							"where itemID=?");
			// Parameters start with 1
			ptmst.setString(1, item.getFile());
			ptmst.setString(2,item.getItemName());
			ptmst.setString(3, item.getPrice());
			ptmst.setString(4, item.getCount());
			ptmst.setString(5, item.getContext());
			
			ptmst.setString(6, Integer.toString(item.getItemID()));
			
			ptmst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}