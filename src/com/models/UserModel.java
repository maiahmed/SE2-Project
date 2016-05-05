package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.mysql.jdbc.Statement;

public class UserModel {

	private String name;
	private String email;
	private String pass;
	private static Integer id;
	private Double lat;
	private Double lon;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserModel [name=" + name + ", email=" + email + ", pass=" + pass + ", id=" + id + ", lat=" + lat
				+ ", lon=" + lon + "]";
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public static UserModel getPostion(int id) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select lang ,lat from users where `id`=?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			UserModel user = new UserModel();
			user.lat = rs.getDouble(1);
			user.lon = rs.getDouble(2);
			System.out.println("lat :" + user.lat);
			System.out.println("lang :" + user.lon);
			return user;

		}

		return null;

	}

	public static UserModel addNewUser(String name, String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into users (`name`,`email`,`password`) VALUES  (?,?,?)";
			// System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, pass);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
			return null;
		} catch (SQLException e) { 
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("lang");
				return user;
			}
			return null;
		} catch (SQLException e) { 
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `lang` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static UserModel addPlace(String name, String describtion, double lat, double lon, int ownerID) {
		System.out.println("I'm in addPlace");
		System.out.println(name);
		System.out.println(describtion);
		System.out.println(lat);
		System.out.println(lon);
		System.out.println(ownerID);
		try { 
			Connection conn = DBConnection.getActiveConnection();

			String sql = "SELECT name FROM places where `name` = ? ";

			System.out.println("==> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			Vector<String> Places = new Vector<String>();
			// Vector<Integer> checkin_id=new Vector<Integer>();
			System.out.println("==> " + stmt);
			while (rs.next()) {
				Places.add(rs.getString("name"));
				// json[i]=new json();
			}
			if (Places.size() == 0) {
			 sql = "Insert into places (`name` ,`description` ,`lat` ,`long`,`ownerID`) VALUES  (?,?,?,?,?)";
			System.out.println("here" + sql);

			//PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, describtion);
			stmt.setDouble(3, lat);
			stmt.setDouble(4, lon);
			stmt.setInt(5, ownerID);

			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				return user;
			}

		} 
			else System.out.println("This Place Is Here");	
		
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static UserModel like(int userId, int checkInID) {

		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into likes (`userid` ,`checkid`) VALUES  (?,?)";
			System.out.println("hereee" + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, userId);
			stmt.setInt(2, checkInID);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				// user.email = email;
				// user.pass = pass;
				// user.name = name;
				// user.lat = 0.0;
				// user.lon = 0.0;
				return user;
			}
			sql = "SELECT UserID FROM checkin where `CheckInID` = ?";
			System.out.println("hereee" + sql);

			// PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, checkInID);
			// stmt.setInt(2, checkInID);

			rs = stmt.executeQuery();
			// rs = stmt.getGeneratedKeys();
			int notifyID = 0;
			if (rs.next()) {
				notifyID = rs.getInt("UserID");

			}
			converter c = new converter(new Like());
			System.out.println(" REsult :");
			c.executeStrategy(userId, notifyID, checkInID);
			// sql = "INSERT INTO `notifications`( `performerID`, `receiverID`,
			// `notificationType`, `checkInID`)"
			// + " VALUES (?,?,?,?)";
			// System.out.println("hereee" + sql);
			//
			// // PreparedStatement stmt;
			// stmt = conn.prepareStatement(sql,
			// Statement.RETURN_GENERATED_KEYS);
			// stmt.setInt(1, userId);
			// stmt.setInt(2, notifyID);
			// stmt.setInt(3, 1);
			// stmt.setInt(4, checkInID);
			//
			// // stmt.setInt(2, checkInID);
			//
			// stmt.executeUpdate();
			// rs = stmt.getGeneratedKeys();
			// sql = "INSERT INTO `notifiedusers`(`checkInID`, `UserID`) VALUES
			// (?,?)";
			// System.out.println(sql);
			// stmt = conn.prepareStatement(sql,
			// Statement.RETURN_GENERATED_KEYS);
			// stmt.setInt(1, checkInID);
			//
			// stmt.setInt(2, userId);
			//
			// stmt.executeUpdate();
			// rs = stmt.getGeneratedKeys();
			// sql = "SELECT `UserID` FROM `notifiedusers` WHERE `checkInID`= ?
			// and UserID!= ? ";
			// System.out.println("hereee" + sql);
			//
			// // PreparedStatement stmt;
			// stmt = conn.prepareStatement(sql,
			// Statement.RETURN_GENERATED_KEYS);
			// stmt.setInt(1, checkInID);
			// stmt.setInt(2, userId);
			//
			// // stmt.setInt(2, checkInID);
			//
			// rs = stmt.executeQuery();
			// // rs = stmt.getGeneratedKeys();
			// Vector<Integer> v = new Vector<Integer>();
			// while (rs.next()) {
			// v.add(rs.getInt("UserID"));
			// }
			// for (int i = 0; i < v.size(); i++)
			// System.out.println(v.get(i));
			// for (int i = 0; i < v.size(); i++) {
			// sql = "INSERT INTO `notifications`( `performerID`, `receiverID`,
			// `notificationType`, `checkInID`)"
			// + " VALUES (?,?,?,?)";
			// System.out.println("hereee" + sql);
			//
			// // PreparedStatement stmt;
			// stmt = conn.prepareStatement(sql,
			// Statement.RETURN_GENERATED_KEYS);
			// stmt.setInt(1, userId);
			// stmt.setInt(2, v.get(i));
			// stmt.setInt(3, 1);
			// stmt.setInt(4, checkInID);
			// stmt.executeUpdate();
			// rs = stmt.getGeneratedKeys();
			// }
			// sql = "INSERT INTO `actions`( `pid`, `type`, `checkin`) VALUES
			// (?,?,?)";
			// System.out.println("hereee" + sql);
			//
			// // PreparedStatement stmt;
			// stmt = conn.prepareStatement(sql,
			// Statement.RETURN_GENERATED_KEYS);
			// stmt.setInt(1, userId);
			// stmt.setInt(2, 1);
			// stmt.setInt(3, checkInID);
			// stmt.executeUpdate();
			// rs = stmt.getGeneratedKeys();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static UserModel comment(int userId, int checkInID, String commentText) {

		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into comments (`userID` ,`checkinID`, `commentText`) VALUES  (?,?,?)";
			System.out.println("In Comment: " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, userId);
			stmt.setInt(2, checkInID);
			stmt.setString(3, commentText);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				// return user;
			}
			sql = "SELECT UserID FROM checkin where `CheckInID` = ?";
			System.out.println("hereee" + sql);

			// PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, checkInID);
			// stmt.setInt(2, checkInID);

			rs = stmt.executeQuery();
			// rs = stmt.getGeneratedKeys();
			int notifyID = 0;
			if (rs.next()) {
				notifyID = rs.getInt("UserID");

			}
			converter c = new converter(new Comment());
			System.out.println(" REsult :");
			c.executeStrategy(userId, notifyID, checkInID);
			/*
			 * sql =
			 * "INSERT INTO `notifications`( `performerID`, `receiverID`, `notificationType`, `checkInID`)"
			 * + " VALUES (?,?,?,?)"; System.out.println("hereee" + sql);
			 * 
			 * stmt = conn.prepareStatement(sql,
			 * Statement.RETURN_GENERATED_KEYS); stmt.setInt(1, userId);
			 * stmt.setInt(2, notifyID); stmt.setInt(3, 2); stmt.setInt(4,
			 * checkInID);
			 * 
			 * // stmt.setInt(2, checkInID);
			 * 
			 * stmt.executeUpdate(); rs = stmt.getGeneratedKeys(); sql =
			 * "INSERT INTO `notifiedusers`(`checkInID`, `UserID`) VALUES (?,?)"
			 * ; System.out.println(sql); stmt = conn.prepareStatement(sql,
			 * Statement.RETURN_GENERATED_KEYS); stmt.setInt(1, checkInID);
			 * 
			 * stmt.setInt(2, userId);
			 * 
			 * stmt.executeUpdate(); rs = stmt.getGeneratedKeys(); sql =
			 * "SELECT `UserID` FROM `notifiedusers` WHERE `checkInID`= ? and UserID!= ? and UserID!=?"
			 * ; System.out.println("hereee" + sql);
			 * 
			 * // PreparedStatement stmt; stmt = conn.prepareStatement(sql,
			 * Statement.RETURN_GENERATED_KEYS); stmt.setInt(1, checkInID);
			 * stmt.setInt(2, userId); stmt.setInt(3, notifyID);
			 * 
			 * // stmt.setInt(2, checkInID);
			 * 
			 * rs = stmt.executeQuery(); // rs = stmt.getGeneratedKeys();
			 * Vector<Integer> v = new Vector<Integer>(); while (rs.next()) {
			 * v.add(rs.getInt("UserID")); } System.out.println("MY Vector");
			 * for (int i = 0; i < v.size(); i++) System.out.println(v.get(i));
			 * for (int i = 0; i < v.size(); i++) { sql =
			 * "INSERT INTO `notifications`( `performerID`, `receiverID`, `notificationType`, `checkInID`)"
			 * + " VALUES (?,?,?,?)"; System.out.println("hereee" + sql);
			 * 
			 * // PreparedStatement stmt; stmt = conn.prepareStatement(sql,
			 * Statement.RETURN_GENERATED_KEYS); stmt.setInt(1, userId);
			 * stmt.setInt(2, v.get(i)); stmt.setInt(3, 2); stmt.setInt(4,
			 * checkInID); stmt.executeUpdate(); rs = stmt.getGeneratedKeys(); }
			 * sql =
			 * "INSERT INTO `actions`( `pid`, `type`, `checkin`) VALUES (?,?,?)"
			 * ; System.out.println("hereee" + sql);
			 * 
			 * // PreparedStatement stmt; stmt = conn.prepareStatement(sql,
			 * Statement.RETURN_GENERATED_KEYS); stmt.setInt(1, userId);
			 * stmt.setInt(2, 2); stmt.setInt(4, checkInID);
			 * stmt.executeUpdate(); rs = stmt.getGeneratedKeys();
			 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static UserModel SavePlaces(int placeid, int ownerID) {
		System.out.println("jjjjjjjjjjjjjjjjj");

		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into savedplaces (`userID`,`placeID`) VALUES  (?,?)";
			System.out.println("hereee" + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, ownerID);
			stmt.setInt(2, placeid);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				// user.email = email;
				// user.pass = pass;
				// user.name = name;
				// user.lat = 0.0;
				// user.lon = 0.0;
				return user;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static Vector<String> makecheckin(String name, int id) {
		try {
			Connection conn = DBConnection.getActiveConnection();

			String sql = "SELECT name FROM places where `name` = ? ";

			System.out.println("==> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			Vector<String> Places = new Vector<String>();
			// Vector<Integer> checkin_id=new Vector<Integer>();
			System.out.println("==> " + stmt);
			while (rs.next()) {
				Places.add(rs.getString("name"));
				// json[i]=new json();
			}
			if (Places.size() > 0) {
				sql = "Insert into checkin (`PlaceName` ,`UserID`) VALUES  (?,?)";
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.setInt(2, id);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				sql = "SELECT `CheckInID` FROM `checkin` WHERE `PlaceName`=? and `UserID`=? ";
				System.out.println("==> " + sql);

				// PreparedStatement stmt;
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, id);

				stmt.setString(2, name);
				rs = stmt.executeQuery();
				int CheckInID = 0;
				if (rs.next()) {
					CheckInID = rs.getInt("CheckInID");
				}
				sql = "INSERT INTO `actions`( `pid`, `type`,`checkin`, `CheckInName`) VALUES (?,?,?,?)";
				System.out.println("hereee" + sql);

				// PreparedStatement stmt;
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, id);
				stmt.setInt(2, 3);
				stmt.setInt(3, CheckInID);

				stmt.setString(4, name);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				return Places;
			} 
			else  
			{
				Places.add("You Can't Make CheckIn"); 
				return Places;
			}
				//System.out.println("You Can't Add This Place");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static Vector<String> showcheckin(int id) {
		Vector<String> ans = new Vector<String>();
		// name

		try {
			Connection conn = DBConnection.getActiveConnection();

			String sql = "SELECT PlaceName FROM checkin where `CheckInID` = ? ";

			System.out.println("==> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				ans.addElement(rs.getString("PlaceName"));
				ans.add(Integer.toString(id));
			}
			sql = "SELECT UserID FROM checkin where `CheckInID` = ? ";

			System.out.println("==> " + sql);
			int userid = 0;
			// PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				userid = rs.getInt("UserID");

			}

			sql = "SELECT name FROM users where `id` = ? ";

			System.out.println("==> " + sql);
			String name = null;
			// PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, userid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				name = rs.getString("name");

			}

			ans.add(name);

			sql = "SELECT userid FROM likes where `checkid` = ? ";

			System.out.println("==> " + sql);

			// PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			Vector<Integer> likers_id = new Vector<Integer>(); //
			while (rs.next()) {
				likers_id.addElement(rs.getInt("userid"));

			}
			ans.add(Integer.toString(likers_id.size()));
			for (int i = 0; i < likers_id.size(); i++) {
				sql = "SELECT name FROM users where `id` = ? ";

				System.out.println("==> " + sql);

				// PreparedStatement stmt;
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, likers_id.get(i));
				rs = stmt.executeQuery();
				if (rs.next()) {
					ans.add(rs.getString("name"));
				}
			}
			sql = "SELECT userID,commentText FROM comments where `checkinID` = ? ";

			System.out.println("==> " + sql);

			// PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			Vector<Integer> commenter_id = new Vector<Integer>(); //
			Vector<String> comments = new Vector<String>();
			while (rs.next()) {
				commenter_id.addElement(rs.getInt("userID"));
				comments.addElement(rs.getString("commentText"));
			}
			ans.add(Integer.toString(commenter_id.size()));
			for (int i = 0; i < commenter_id.size(); i++) {
				String sql1 = "SELECT name FROM users where `id` = ? ";

				System.out.println("==> " + sql1);

				PreparedStatement stmt1;
				stmt1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);

				stmt1.setInt(1, commenter_id.get(i));
				ResultSet rs1 = stmt1.executeQuery();
				if (rs1.next()) {
					ans.add(rs1.getString("name"));
					ans.add(comments.get(i));

				}
			}
			for (int i = 0; i < ans.size(); i++)
				System.out.println(ans.get(i));
			return ans;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static Vector<String> getcheckin(int id) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			Vector<Integer> IDS = new Vector<Integer>();
			Vector<String> names = new Vector<String>();
			IDS.add(id);
			String sql = "SELECT PlaceName , CheckInID FROM checkin where `UserID` = ? ";

			System.out.println("==> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			Vector<String> Places = new Vector<String>();
			Vector<Integer> checkin_id = new Vector<Integer>();

			System.out.println("==> " + stmt);
			while (rs.next()) {
				Places.add(rs.getString("PlaceName"));
				checkin_id.add(rs.getInt("CheckInID"));
				// SELECT `notificationID`, `performerID`, `receiverID`,
				// `notificationType`, `checkInID` FROM `notifications` WHERE 1
			}

			// ============================================================

			Vector<Integer> Followers = new Vector<Integer>();
			sql = "SELECT id FROM users, follower where `idFollower2` = id and `idFollower1` = ?";

			System.out.println("==> " + sql);

			// stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Followers.add(rs.getInt("id"));

			}
			System.out.println("My Followers");
			for (int j = 0; j < Followers.size(); j++) {
				System.out.println(Followers.get(j));
				IDS.add(Followers.get(j));
			}
			for (int i = 0; i < Followers.size(); i++) {
				sql = "SELECT PlaceName , CheckInID FROM checkin where `UserID` = ? ";

				System.out.println("==> " + sql);

				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, Followers.get(i));
				rs = stmt.executeQuery();

				System.out.println("==> " + stmt);
				while (rs.next()) {
					checkin_id.add(rs.getInt("CheckInID"));

					Places.add(rs.getString("PlaceName"));
				}
				// now i need to select the places which my followers make like
				// on
				sql = "SELECT checkid FROM likes where `userid` = ? ";

				System.out.println("==> " + sql);

				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, Followers.get(i));
				rs = stmt.executeQuery();
				Vector<Integer> likedcheckin = new Vector<Integer>();
				System.out.println("==> " + stmt);
				while (rs.next()) {
					likedcheckin.add(rs.getInt("checkid"));
				}
				// now i have all the ids of checkins which my follower make
				for (int k = 0; k < likedcheckin.size(); k++) {
					sql = "SELECT PlaceName , CheckInID FROM checkin where `CheckinID` = ? ";

					System.out.println("==> " + sql);

					// PreparedStatement stmt;
					stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					stmt.setInt(1, likedcheckin.get(k));
					rs = stmt.executeQuery();

					System.out.println("==> " + stmt);
					while (rs.next()) {
						Places.add(rs.getString("PlaceName"));
						checkin_id.add(rs.getInt("CheckInID"));
					}
				}
				// now we need to work on comment
				sql = "SELECT checkinID FROM comments where `userID` = ? ";

				System.out.println("==> " + sql);

				// PreparedStatement stmt;
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, Followers.get(i));
				rs = stmt.executeQuery();
				Vector<Integer> commentedcheckin = new Vector<Integer>();
				System.out.println("==> " + stmt);
				while (rs.next()) {
					commentedcheckin.add(rs.getInt("checkinID"));
				}
				// now i have all the ids of checkins which my follower make
				for (int l = 0; l < commentedcheckin.size(); l++) {
					sql = "SELECT PlaceName , CheckInId FROM checkin where `CheckinID` = ? ";

					System.out.println("==> " + sql);

					// PreparedStatement stmt;
					stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

					stmt.setInt(1, commentedcheckin.get(l));
					rs = stmt.executeQuery();

					System.out.println("==> " + stmt);
					while (rs.next()) {
						Places.add(rs.getString("PlaceName"));
						checkin_id.add(rs.getInt("CheckInID"));

					}
				}

			}

			Vector<String> langandlat = new Vector<String>();
			System.out.println("Esraaaaaaaaa");
			for (int i = 0; i < Places.size(); i++)
				System.out.println(Places.get(i));
			for (int i = 0; i < Places.size(); i++) {// SELECT `lat`, `long`
														// FROM `places` WHERE
														// `name`='giza'

				String sql1 = "SELECT `lat`, `long` FROM `places` WHERE `name`= ? ";

				System.out.println("==> " + sql1);

				PreparedStatement stmt1;
				stmt1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);

				System.out.println("rrrr" + Places.get(i));
				stmt1.setString(1, Places.get(i));
				ResultSet rs1 = stmt1.executeQuery();
				System.out.println("==> " + stmt1);
				if (rs1.next()) {
					// Places.add(rs.getString("PlaceName"));
					// String lat=rs1.getDouble("lat")
					Double f = rs1.getDouble("lat");
					System.out.println("f" + f);
					Double s = rs1.getDouble("long");
					System.out.println("s" + s);
					String lat = Double.toString(f);
					String lang = Double.toString(s);
					langandlat.add(lat);
					langandlat.add(lang);
				}
			}
			String sql2 = "SELECT `lat`,`lang` FROM users where `id` = ? ";

			System.out.println("==> " + sql2);

			PreparedStatement stmt2;
			stmt2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);

			stmt2.setInt(1, id);
			ResultSet rs2 = stmt2.executeQuery();
			System.out.println("==> " + stmt2);
			Vector<String> res = new Vector<String>();

			if (rs2.next()) {
				res.add(Double.toString((rs2.getDouble("lat"))));
				res.add(Double.toString((rs2.getDouble("lang"))));

			}
			System.out.println("My array");
			for (int i = 0; i < langandlat.size(); i++)
				System.out.println(langandlat.get(i));
			int cnt = 0;
			int j = 0;
			for (int i = 0; i < Places.size(); i++) {
				res.add(Places.get(i));
				res.add(langandlat.get(cnt));
				cnt++;
				res.add(langandlat.get(cnt));
				cnt++;
				res.add(Integer.toString(checkin_id.get(j)));
				j++;
			}
			System.out.println("Final Array");
			for (int i = 0; i < res.size(); i++) {
				System.out.println(res.get(i));
			}
			return res;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// int performerID, int notificationType, int checkInID
	public static UserModel notifay(int performerID, int notificationType, int checkInID) {
		// TODO Auto-generated method stub

		return null;
	}

	public static Vector<String> Notification(int id) {
		Vector<Integer> PerformedID = new Vector<Integer>();
		Vector<Integer> NotifyType = new Vector<Integer>();
		Vector<Integer> CheckinID = new Vector<Integer>();
		Vector<String> PerformedName = new Vector<String>();
		Vector<String> NotifyName = new Vector<String>();
		Vector<String> CheckinName = new Vector<String>();
		Vector<String> FinalAns = new Vector<String>();

		try {// receiverID
			Connection conn = DBConnection.getActiveConnection();

			String sql = "SELECT `performerID`, `notificationType`, `checkInID` FROM `notifications` WHERE `receiverID`=? ";

			System.out.println("==> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				PerformedID.add(rs.getInt("performerID"));
				NotifyType.add(rs.getInt("notificationType"));
				CheckinID.add(rs.getInt("checkInID"));
			}
			for (int i = 0; i < NotifyType.size(); i++) {
				if (NotifyType.get(i).equals(1)) {
					NotifyName.add("Like");
				} else if (NotifyType.get(i).equals(2)) {
					NotifyName.add("Comment");
				}

			}
			for (int i = 0; i < PerformedID.size(); i++) {

				// SELECT `id`, `name`, `email`, `password`, `lat`, `lang` FROM
				// `users` WHERE 1

				sql = "SELECT `name` FROM `users` WHERE `id`=? ";

				System.out.println("==> " + sql);

				// PreparedStatement stmt;
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, PerformedID.get(i));
				rs = stmt.executeQuery();

				while (rs.next()) {
					PerformedName.add(rs.getString("name"));
				}

			}
			for (int i = 0; i < CheckinID.size(); i++) {
				sql = "SELECT `PlaceName` FROM `checkin` WHERE `CheckInID`=? ";

				System.out.println("==> " + sql);

				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, CheckinID.get(i));
				rs = stmt.executeQuery();

				while (rs.next()) {
					CheckinName.add(rs.getString("PlaceName"));
				}
			}
			// PerformedName , NotifyName , CheckinName , CheckinID
			for (int i = 0; i < PerformedID.size(); i++) {
				FinalAns.addElement(PerformedName.get(i));
				FinalAns.addElement(NotifyName.get(i));
				FinalAns.addElement(CheckinName.get(i));
				FinalAns.addElement(Integer.toString(CheckinID.get(i)));

			}

			System.out.println(FinalAns.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return FinalAns;
	}

	public static Vector<String> getHistory(int id) {
		Vector<Integer> type = new Vector<Integer>();
		Vector<Integer> checkin = new Vector<Integer>();
		Vector<String> Name = new Vector<String>();
		Vector<String> res = new Vector<String>();
		Vector<Integer> type_Name = new Vector<Integer>();

		try {// receiverID
			Connection conn = DBConnection.getActiveConnection();

			String sql = "SELECT   `type`, `checkin`, `CheckInName` FROM `actions` WHERE `pid`=? ";

			System.out.println("==> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("type") == 1) {
					type.add(1);
					checkin.add(rs.getInt("checkin"));

				} else if (rs.getInt("type") == 2) {
					type.add(2);
					checkin.add(rs.getInt("checkin"));

				} else if (rs.getInt("type") == 3) {
					type_Name.add(rs.getInt("checkin"));
					Name.add(rs.getString("CheckInName"));

				}

			}
			for (int i = 0; i < checkin.size(); i++) {
				sql = "SELECT `PlaceName` FROM `checkin` WHERE `CheckInID`=? ";

				System.out.println("==> " + sql);

				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				stmt.setInt(1, checkin.get(i));
				rs = stmt.executeQuery();

				while (rs.next()) {
					if (type.get(i) == 1) {
						res.add("Like");
					} else if (type.get(i) == 2) {
						res.add("Comment");
					}
					res.add(rs.getString("PlaceName"));
					res.add(Integer.toString(checkin.get(i)));
				}
			}
			for (int i = 0; i < Name.size(); i++) {
				res.add("Check in");
				res.add(Name.get(i));
				res.add(Integer.toString(type_Name.get(i)));
			}
			return res;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static void DeleteHistory(int userid, int type, int checkID) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			// `ID`, `pid`, `type`, `checkin`, `CheckInName`
			String sql = "DELETE FROM `actions` WHERE `pid`=? and `type`=? and `checkin`=?  ";

			System.out.println("==> " + sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, userid);
			stmt.setInt(2, type);
			stmt.setInt(3, checkID);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				System.out.println(userid);
				System.out.println(type);
			}
			if (type == 1) {
				// `ID`, `pid`, `type`, `checkin`, `CheckInName`
				sql = "DELETE FROM `likes` WHERE `userid`=? and `checkid`=?  ";

				System.out.println("==> " + sql);

				// PreparedStatement stmt;
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, userid);
				stmt.setInt(2, checkID);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					System.out.println(userid);
				}

			} else if (type == 2) {
				// `ID`, `pid`, `type`, `checkin`, `CheckInName`
				sql = "DELETE FROM `comments` WHERE `checkinID`=? `userID`=? ";

				System.out.println("==> " + sql);

				// PreparedStatement stmt;
				stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				stmt.setInt(1, checkID);
				stmt.setInt(2, userid);

				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					System.out.println(userid);
				}
			} else if (type == 3) {
				/*
				 * sql =
				 * "DELETE FROM `checkin` WHERE `UserID`=? and `CheckInID`=? ";
				 * 
				 * System.out.println("==> " + sql);
				 * 
				 * // PreparedStatement stmt; stmt = conn.prepareStatement(sql,
				 * Statement.RETURN_GENERATED_KEYS); stmt.setInt(1, userid);
				 * stmt.setInt(2, checkID);
				 * 
				 * stmt.executeUpdate(); rs = stmt.getGeneratedKeys(); if
				 * (rs.next()) { System.out.println(userid); }
				 */
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}
