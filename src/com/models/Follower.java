package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.json.simple.JSONObject;

import com.mysql.jdbc.Statement;

public class Follower {
	private Integer id1;
	private Integer id2;

	public Integer getId1() {
		return id1;
	}

	public void setId1(Integer id1) {
		this.id1 = id1;
	}

	public Integer getId2() {
		return id2;
	}

	public void setId2(Integer id2) {
		this.id2 = id2;
	}

	public static Follower deleteFollower(int id1, int id2) {
		try {
			Connection conn = DBConnection.getActiveConnection();

			String sql = "delete from follower where `idFollower1` = ? and `idFollower2` = ?";
			System.out.println(sql);
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id1);
			stmt.setInt(2, id2);

			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				Follower follower = new Follower();
				follower.id1 = id1;
				follower.id2 = id2;

				return follower;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Follower addFollower(int id1, int id2) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			// insert into follower values (1,2);
			String sql = "Insert into follower (`idFollower1`,`idFollower2`) VALUES  (?,?)";
			System.out.println("-=-=-=> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id1);
			stmt.setInt(2, id2);
		    stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) { 
				//System.out.println("hereeeeeeeeeeee");
				Follower follower = new Follower();
				follower.id1 = id1;
				follower.id2 = id2;

				return follower;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
	public static Vector<UserModel> GetFollowers(int userID) {
		try {
			Connection conn = DBConnection.getActiveConnection();

			String sql = "SELECT name, email FROM users, follower where `idFollower2` = id and `idFollower1` = ?";

			 System.out.println("==> "+sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, userID);
			ResultSet rs=stmt.executeQuery();
			Vector<UserModel> users = new Vector<UserModel>();
			 System.out.println("==> "+stmt);
			while (rs.next()) {  
				UserModel user = new UserModel();
				user.setEmail ( rs.getString("email"));

				user.setName ( rs.getString("name")); 
				System.out.println("Name : " +user.getName()); 
				System.out.println("Email : " +user.getEmail());  
			//	json[i]=new json();  
				users.addElement(user);
					
				
			} 
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "Follower [id1=" + id1 + ", id2=" + id2 + "]";
	}

}
