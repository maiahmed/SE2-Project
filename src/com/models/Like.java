package com.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Statement;

public class Like implements StrategyPattern {

	@Override
	public void update(int userId, int notifyID, int checkInID) {
		// TODO Auto-generated method stub
		try {
			Connection conn = DBConnection.getActiveConnection();
		 String sql = "INSERT INTO `notifications`( `performerID`, `receiverID`, `notificationType`, `checkInID`)"
				+ " VALUES (?,?,?,?)";
		System.out.println("hereee" + sql);

		 PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, userId);
		stmt.setInt(2, notifyID);
		stmt.setInt(3, 1);
		stmt.setInt(4, checkInID);

		// stmt.setInt(2, checkInID);

		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		sql = "INSERT INTO `notifiedusers`(`checkInID`, `UserID`) VALUES (?,?)";
		System.out.println(sql);
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, checkInID);

		stmt.setInt(2, userId);

		stmt.executeUpdate();
		rs = stmt.getGeneratedKeys();
		sql = "SELECT `UserID` FROM `notifiedusers` WHERE `checkInID`= ? and UserID!= ? ";
		System.out.println("hereee" + sql);

		// PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, checkInID);
		stmt.setInt(2, userId);

		// stmt.setInt(2, checkInID);

		rs = stmt.executeQuery();
		// rs = stmt.getGeneratedKeys();
		Vector<Integer> v = new Vector<Integer>();
		while (rs.next()) {
			v.add(rs.getInt("UserID"));
		}
		for (int i = 0; i < v.size(); i++)
			System.out.println(v.get(i));
		for (int i = 0; i < v.size(); i++) {
			sql = "INSERT INTO `notifications`( `performerID`, `receiverID`, `notificationType`, `checkInID`)"
					+ " VALUES (?,?,?,?)";
			System.out.println("hereee" + sql);

			// PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, userId);
			stmt.setInt(2, v.get(i));
			stmt.setInt(3, 1);
			stmt.setInt(4, checkInID);
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
		}
		sql = "INSERT INTO `actions`( `pid`, `type`, `checkin`) VALUES (?,?,?)";
		System.out.println("hereee" + sql);

		// PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, userId);
		stmt.setInt(2, 1);
		stmt.setInt(3, checkInID);
		stmt.executeUpdate();
		rs = stmt.getGeneratedKeys(); } 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
