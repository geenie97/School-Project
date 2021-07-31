package com.daniel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.daniel.model.User;
import com.daniel.util.userDbUtil;

public class UserDao {

	private Connection connection;

	public UserDao() {
		connection = userDbUtil.getConnection();
	}

	public int addUser(User user) {
		if(connection == null) return -3;
		try {
			
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into users(userID,password,Name,Address,Phone,email) values (?, ?,?,?, ?, ? )");
			// Parameters start with 1
			preparedStatement.setString(1, user.getUserId());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getAddress());
			preparedStatement.setString(5, user.getPhone());
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.executeUpdate();
			
			//connection.close();
			//preparedStatement.close();
			return 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public void deleteUser(String userId) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from users where userid=?");
			// Parameters start with 1
			preparedStatement.setString(1, userId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUser(User user) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("update users set Password=?, Name=?,address=?,phone=? ,email=?" +
							"where userid=?");
			// Parameters start with 1
			preparedStatement.setString(1, user.getUserId());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getAddress());
			preparedStatement.setString(5, user.getPhone());
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from users");
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("userid"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	
	public User getUserById(String userId) {
		User user = new User();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from users where userid=?");
			preparedStatement.setString(1, userId);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				user.setUserId(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setAddress(rs.getString("address"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	
	public String userLogin(String userId,String pw) {
		User user = new User();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from users where userid=?");
			preparedStatement.setString(1, userId);
			ResultSet rs = preparedStatement.executeQuery();
			
		
			if (rs.next()) {
				user.setUserId(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name")); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(user.getPassword()==null) return "No";
		else if(Integer.parseInt(user.getPassword())==Integer.parseInt(pw)) { 
				
				
				
				return user.getName();
		
		
		
		}else return "No";
	}
}
