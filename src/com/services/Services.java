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
	@POST
	@Path("/unFollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unFollow(@FormParam("idFollower1") String idFollower1,
			@FormParam("idFollower2") String idFollower2) {

		Follower follwer = Follower.deleteFollower(
				Integer.parseInt(idFollower1), Integer.parseInt(idFollower2));

		JSONObject json = new JSONObject();
		json.put("idFollower1", idFollower1);
		json.put("idFollower2", idFollower2);

		return json.toJSONString();

	}

	@POST
	@Path("/Follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String Follow(@FormParam("idFollower1") String idFollower1,
			@FormParam("idFollower2") String idFollower2) {

		Follower follwer = Follower.addFollower(Integer.parseInt(idFollower1),
				Integer.parseInt(idFollower2));

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
			json.put("email",(v.get(i)).getEmail());
			ja.add(json);
			
		}

		return ja.toJSONString();

	}

	@POST
	@Path("/GetLastPostion")
	@Produces(MediaType.TEXT_PLAIN)
	public String getlastpostion(@FormParam("id") String id)
			throws NumberFormatException, SQLException {
		// UserModel user;

		UserModel user = UserModel.getPostion(Integer.parseInt(id));
		JSONObject json = new JSONObject();

		// System.out.println(user.getId());
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("lang", user.getLon());

		return json.toJSONString();
	}

	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
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

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
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

	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id),
				Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
}
