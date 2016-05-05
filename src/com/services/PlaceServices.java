package com.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.UserModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

@Path("/")
public class PlaceServices {
	@POST
	@Path("/SavePlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String savePlace(@FormParam("placeid")String placeid,@FormParam("id") String id) {

		System.out.println("heeeeeer");
		System.out.println(placeid);
		System.out.println(id);
		UserModel user = UserModel.SavePlaces(Integer.parseInt(placeid),Integer.parseInt(id));
		System.out.println("this my user " + user);
		JSONObject json = new JSONObject();
		// json.put("lat", lat);
		// json.put("long",lang );
		json.put("id", id);
		return json.toJSONString();

	}
	
	@POST
	@Path("/AddNewPlace")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlace(@FormParam("name") String name, @FormParam("description") String description,
			@FormParam("lat") String lat, @FormParam("long") String lang, @FormParam("id") String id) {

		System.out.println("heeeeeer");
		System.out.println(name);
		System.out.println(description);
		System.out.println(lat);
		System.out.println(lang);
		System.out.println(id);
		UserModel user = UserModel.addPlace(name, description, Double.parseDouble(lat), Double.parseDouble(lang),
				Integer.parseInt(id));
		System.out.println("this my user " + user);
		JSONObject json = new JSONObject();
		// json.put("lat", lat);
		// json.put("long",lang );
		json.put("id", id);
		return json.toJSONString();

	}
	
	@POST
	@Path("/CheckIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlace(@FormParam("name") String name,@FormParam("id")String id) {
 
		System.out.println("heeeeeer");
		System.out.println(name);
 
		Vector<String> v = new Vector<String>();
		v = UserModel.makecheckin(name,Integer.parseInt(id));
 
		JSONArray ja = new JSONArray();
		for (int i = 0; i < v.size(); i++) {
			JSONObject json = new JSONObject();
			json.put("name", v.get(i));
		System.out.println(v.get(i));
			ja.add(json);
 
		}
 
		return ja.toJSONString();
 
	}
	@POST
	@Path("/GetLastPostion")
	@Produces(MediaType.TEXT_PLAIN)
	public String getlastpostion(@FormParam("id") String id) throws NumberFormatException, SQLException {
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
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id, @FormParam("lat") String lat,
			@FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat),
				Double.parseDouble(lon));
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
