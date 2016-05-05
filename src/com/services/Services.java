package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.Follower;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */
//	@POST
//	@Path("/unFollow")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String unFollow(@FormParam("idFollower1") String idFollower1, @FormParam("idFollower2") String idFollower2) {
//
//		Follower follwer = Follower.deleteFollower(Integer.parseInt(idFollower1), Integer.parseInt(idFollower2));
//
//		JSONObject json = new JSONObject();
//		json.put("idFollower1", idFollower1);
//		json.put("idFollower2", idFollower2);
//
//		return json.toJSONString();
//
//	}
//
//	@POST
//	@Path("/Follow")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String Follow(@FormParam("idFollower1") String idFollower1, @FormParam("idFollower2") String idFollower2) {
//
//		Follower follwer = Follower.addFollower(Integer.parseInt(idFollower1), Integer.parseInt(idFollower2));
//
//		JSONObject json = new JSONObject();
//		json.put("idFollower1", idFollower1);
//		json.put("idFollower2", idFollower2);
//
//		return json.toJSONString();
//
//	}
//
//	@POST
//	@Path("/AddNewPlace")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String addPlace(@FormParam("name") String name, @FormParam("description") String description,
//			@FormParam("lat") String lat, @FormParam("long") String lang, @FormParam("id") String id) {
//
//		System.out.println("heeeeeer");
//		System.out.println(name);
//		System.out.println(description);
//		System.out.println(lat);
//		System.out.println(lang);
//		System.out.println(id);
//		UserModel user = UserModel.addPlace(name, description, Double.parseDouble(lat), Double.parseDouble(lang),
//				Integer.parseInt(id));
//		System.out.println("this my user " + user);
//		JSONObject json = new JSONObject();
//		// json.put("lat", lat);
//		// json.put("long",lang );
//		json.put("id", id);
//		return json.toJSONString();
//
//	}
//	
//	
//	@POST
//	@Path("/CheckIn")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String addPlace(@FormParam("name") String name,@FormParam("id")String id) {
// 
//		System.out.println("heeeeeer");
//		System.out.println(name);
// 
//		Vector<String> v = new Vector<String>();
//		v = UserModel.makecheckin(name,Integer.parseInt(id));
// 
//		JSONArray ja = new JSONArray();
//		for (int i = 0; i < v.size(); i++) {
//			JSONObject json = new JSONObject();
//			json.put("name", v.get(i));
//		System.out.println(v.get(i));
//			ja.add(json);
// 
//		}
// 
//		return ja.toJSONString();
// 
//	}
//	
//	/*@POST
//	@Path("/Like")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String Like(@FormParam("userId") String userID,@FormParam("CheckInId")String CheckInId) {
//		UserModel user = UserModel.like(Integer.parseInt(userID), Integer.parseInt(CheckInId));
//		JSONObject json = new JSONObject();
//		JSONArray ja = new JSONArray();
//		
//		json.put("userId", userID);
//		json.put("CheckInId", CheckInId);
//		ja.add(json);
// 
//		return ja.toJSONString();
// 
//	}
//	
//	@POST
//	@Path("/Comment")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String comment(@FormParam("userId") String userID,@FormParam("CheckInId")String CheckInId, @FormParam("CommentText")String text) {
//		UserModel user = UserModel.comment(Integer.parseInt(userID), Integer.parseInt(CheckInId),text);
//		JSONObject json = new JSONObject();
//		JSONArray ja = new JSONArray();
//		
//		json.put("userId", userID);
//		json.put("CheckInId", CheckInId);
//		json.put("CommentText", text);
//		ja.add(json);
// 
//		return ja.toJSONString();
// 
//	}
//*/
////	@POST
////	@Path("/GetFollowers")
////	@Produces(MediaType.TEXT_PLAIN)
////	public String GetFollowers(@FormParam("idFollower1") String idFollower) {
////		Vector<UserModel> v = new Vector<UserModel>();
////		v = Follower.GetFollowers(Integer.parseInt(idFollower));
////
////		JSONArray ja = new JSONArray();
////		for (int i = 0; i < v.size(); i++) {
////			JSONObject json = new JSONObject();
////			json.put("name", (v.get(i)).getName());
////			json.put("email", (v.get(i)).getEmail());
////			ja.add(json);
////
////		}
////
////		return ja.toJSONString();
////		// return ja;
////	}
//
//	@POST
//	@Path("/GetLastPostion")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getlastpostion(@FormParam("id") String id) throws NumberFormatException, SQLException {
//		// UserModel user;
//
//		UserModel user = UserModel.getPostion(Integer.parseInt(id));
//		JSONObject json = new JSONObject();
//
//		// System.out.println(user.getId());
//		json.put("id", user.getId());
//		json.put("name", user.getName());
//		json.put("email", user.getEmail());
//		json.put("pass", user.getPass());
//		json.put("lat", user.getLat());
//		json.put("lang", user.getLon());
//
//		return json.toJSONString();
//	}
//
	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name, @FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	//showcheckin 
	@POST
	@Path("/showcheckin")
	@Produces(MediaType.TEXT_PLAIN) 
	public String showCheckin(@FormParam("ID") String id) 
	{Vector<String> v = new Vector<String>();
	v = UserModel.showcheckin(Integer.parseInt(id));
	String s=""; 
	for(int i=0;i<v.size();i++) 
	{
		s+=v.get(i); 
		s+=',';
	}
return s;
		
		
	}
	@POST
	@Path("/getcheckins")
	@Produces(MediaType.TEXT_PLAIN)
	public String checkin(@FormParam("ID") String id) {
		Vector<String> v = new Vector<String>();
		v = UserModel.getcheckin(Integer.parseInt(id));
 
		JSONArray ja = new JSONArray();
		for (int i = 0; i < v.size(); i++) {
			JSONObject json = new JSONObject();
			json.put("name", v.get(i));
		System.out.println(v.get(i));
			ja.add(json);
 
		} 
		String s=""; 
		for(int i=0;i<v.size();i++) 
		{
			s+=v.get(i); 
			s+=',';
		}
 return s;
//		return ja.toJSONString();
	}
//	@POST
//	@Path("/SavePlace")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String savePlace(@FormParam("placeid")String placeid,@FormParam("id") String id) {
//
//		System.out.println("heeeeeer");
//		System.out.println(placeid);
//		System.out.println(id);
//		UserModel user = UserModel.SavePlaces(Integer.parseInt(placeid),Integer.parseInt(id));
//		System.out.println("this my user " + user);
//		JSONObject json = new JSONObject();
//		// json.put("lat", lat);
//		// json.put("long",lang );
//		json.put("id", id);
//		return json.toJSONString();
//
//	}


	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

//	@POST
//	@Path("/updatePosition")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String updatePosition(@FormParam("id") String id, @FormParam("lat") String lat,
//			@FormParam("long") String lon) {
//		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat),
//				Double.parseDouble(lon));
//		JSONObject json = new JSONObject();
//		json.put("status", status ? 1 : 0);
//		return json.toJSONString();
//	}
//
//	@GET
//	@Path("/")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getJson() {
//		return "Hello after editing";
//		// Connection URL:
//		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
//	}
}
