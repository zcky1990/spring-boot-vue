package com.zcky.learn.with.teacher.mongoDb.serializer;

import com.google.gson.*;
import com.zcky.learn.with.teacher.mongoDb.model.Article;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class ArticleSerializer implements JsonSerializer<Article> {
	
	@Override
	public JsonElement serialize(Article src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("_id", id);
		
		
		if(jsonObj.has("author")) {
			jsonObj.remove("author");
			Users user = src.getAuthor();
			JsonObject userJson = new JsonObject();
			userJson.addProperty("_id", user.getStringId());
			userJson.addProperty("firstname", user.getFirstname());
			userJson.addProperty("lastname", user.getLastname());
			userJson.addProperty("email", user.getEmail());
			userJson.addProperty("username", user.getUsername());
			userJson.addProperty("name", user.getName());
			userJson.addProperty("image_profile_url", user.getImageUrl());
			jsonObj.add("author", userJson);
		}
				
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		
		return jsonObj;
	}
}
