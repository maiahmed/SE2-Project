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

public class UserInteraction {
	    //getNotification 
	@POST
	@Path("/getNotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String getNotification(@FormParam("ID") String userID) {
		Vector<String> user = UserModel.Notification(Integer.parseInt(userID));
		JSONObject json = new JSONObject();
		JSONArray ja = new JSONArray();
		String res = "";
		for(int i=0;i<user.size(); i++)
		{
			res+=user.get(i)+",";
		}
		json.put("userId", userID);
		//json.put("CheckInId", CheckInId);
		//ja.add(json);
 
		return res;
 
	} 
	//String urlParameters = "userId=" + id + "&type="+t+"&CheckID=" + checkInId;
	@POST
	@Path("/deleteHistory")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteHistory(@FormParam("userId") String userID,@FormParam("type") String type, @FormParam("CheckID") String CheckID) {
		 UserModel.DeleteHistory(Integer.parseInt(userID),Integer.parseInt(type),Integer.parseInt(CheckID));
		JSONObject json = new JSONObject();
		JSONArray ja = new JSONArray();
		String res = "";
	
		return res;
 
	} 
	//history 
	@POST
	@Path("/history")
	@Produces(MediaType.TEXT_PLAIN) //userId
	public String history(@FormParam("userId") String userID) {
		Vector<String>user = UserModel.getHistory(Integer.parseInt(userID));
		JSONObject json = new JSONObject();
		JSONArray ja = new JSONArray();
		
		json.put("userId", userID);
		ja.add(json);
        System.out.println(user.toString()); 
        String res = "";
		for(int i=0;i<user.size(); i++)
		{
			res+=user.get(i)+",";
		}
		return res;
 
	}
		@POST
		@Path("/Like")
		@Produces(MediaType.TEXT_PLAIN)
		public String Like(@FormParam("userId") String userID,@FormParam("CheckInId")String CheckInId) {
			UserModel user = UserModel.like(Integer.parseInt(userID), Integer.parseInt(CheckInId));
			JSONObject json = new JSONObject();
			JSONArray ja = new JSONArray();
			
			json.put("userId", userID);
			json.put("CheckInId", CheckInId);
			ja.add(json);
	 
			return ja.toJSONString();
	 
		}
		//Notify (int performerID, int notificationType, int checkInID
		@POST
		@Path("/Notify")
		@Produces(MediaType.TEXT_PLAIN)
		public String Notify(@FormParam("performerID") String performerID,@FormParam("notificationType")String notificationType ,@FormParam("checkInID")String checkInID) {
			UserModel user = UserModel.notifay(Integer.parseInt(performerID), Integer.parseInt(notificationType),Integer.parseInt(checkInID));
			JSONObject json = new JSONObject();
			JSONArray ja = new JSONArray();
			
		//	json.put("userId", userID);
			//json.put("CheckInId", CheckInId);
			ja.add(json);
	 
			return ja.toJSONString();
	 
		}
		
		
		@POST
		@Path("/Comment")
		@Produces(MediaType.TEXT_PLAIN)
		public String comment(@FormParam("userId") String userID,@FormParam("CheckInId")String CheckInId, @FormParam("CommentText")String text) {
			UserModel user = UserModel.comment(Integer.parseInt(userID), Integer.parseInt(CheckInId),text);
			JSONObject json = new JSONObject();
			JSONArray ja = new JSONArray();
			
			json.put("userId", userID);
			json.put("CheckInId", CheckInId);
			json.put("CommentText", text);
			ja.add(json);
	 
			return ja.toJSONString();
	 
		}

		
		
	}

