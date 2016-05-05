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
public class FollowingServices {
	
	@POST
	@Path("/unFollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unFollow(@FormParam("idFollower1") String idFollower1, @FormParam("idFollower2") String idFollower2) {

		Follower follwer = Follower.deleteFollower(Integer.parseInt(idFollower1), Integer.parseInt(idFollower2));

		JSONObject json = new JSONObject();
		json.put("idFollower1", idFollower1);
		json.put("idFollower2", idFollower2);

		return json.toJSONString();

	}

	@POST
	@Path("/Follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String Follow(@FormParam("idFollower1") String idFollower1, @FormParam("idFollower2") String idFollower2) {

		Follower follwer = Follower.addFollower(Integer.parseInt(idFollower1), Integer.parseInt(idFollower2));

		JSONObject json = new JSONObject();
		json.put("idFollower1", idFollower1);
		json.put("idFollower2", idFollower2);

		return json.toJSONString();

	}

	@POST
	@Path("/GetFollowers")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetFollowers(@FormParam("idFollower1") String idFollower) {
		Vector<UserModel> v = new Vector<UserModel>();
		v = Follower.GetFollowers(Integer.parseInt(idFollower));

		JSONArray ja = new JSONArray();
		for (int i = 0; i < v.size(); i++) {
			JSONObject json = new JSONObject();
			json.put("name", (v.get(i)).getName());
			json.put("email", (v.get(i)).getEmail());
			ja.add(json);

		}

		return ja.toJSONString();
		// return ja;
	}

}
