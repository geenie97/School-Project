package com.daniel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.daniel.model.Board;

import com.daniel.util.boardDbUtil;




public class BoardDao {
	private Connection connection;

	public BoardDao() {
		connection = boardDbUtil.getConnection();
	}
	
	public int addBoard(Board board) {
		if(connection == null) return -3;
		try {

			PreparedStatement pstmt = connection.
					prepareStatement("insert into boards(name,title,content,date)"+"values(?,?,?,NOW())");
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			
			pstmt.executeUpdate();
			return 1;
		}
		catch(Exception ex){
			return -1;
		}
	}
	
	public List<Board> getAllBoards() {
		List<Board> boards = new ArrayList<Board>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from boards");
			while (rs.next()) {
				Board board = new Board();
				board.setNum(Integer.parseInt(rs.getString("num")));
				board.setTitle(rs.getString("title"));
				board.setName(rs.getString("name"));
				board.setDate(rs.getString("date"));
				boards.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return boards;
	}
	
	public Board getBoardById(String num) {
		Board board=new Board();
		try {
			PreparedStatement preparedStatement = connection.
					prepareStatement("select * from boards where num=?");
			preparedStatement.setString(1, num);
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				board.setNum(Integer.parseInt(rs.getString("num")));
				board.setTitle(rs.getString("title"));
				board.setName(rs.getString("name"));
				board.setDate(rs.getString("date"));
				board.setContent(rs.getString("content"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return board;
	}

	public void deleteBoard(String num) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("delete from boards where num=?");
			// Parameters start with 1
			preparedStatement.setString(1, num);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
