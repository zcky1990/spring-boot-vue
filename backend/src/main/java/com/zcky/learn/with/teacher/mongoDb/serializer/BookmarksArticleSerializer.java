package com.zcky.learn.with.teacher.mongoDb.serializer;

import com.google.gson.*;
import com.zcky.learn.with.teacher.mongoDb.model.Article;
import com.zcky.learn.with.teacher.mongoDb.model.BookmarksArticle;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class BookmarksArticleSerializer implements JsonSerializer<BookmarksArticle> {

	@Override
	public JsonElement serialize(BookmarksArticle src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);

		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
			String id = src.getStringId();
			jsonObj.addProperty("id", id);
		}

		if(jsonObj.has("users")) {
			jsonObj.remove("users");
			Users user = src.getUser();
			JsonObject userJson = new JsonObject();
			userJson.addProperty("id", user.getStringId());
			userJson.addProperty("firstname", user.getFirstname());
			userJson.addProperty("lastname", user.getLastname());
			userJson.addProperty("email", user.getEmail());
			userJson.addProperty("username", user.getUsername());
			userJson.addProperty("name", user.getName());
			userJson.addProperty("image_profile_url", user.getImageUrl());
			jsonObj.add("users", userJson);
		}

		if(jsonObj.has("article")) {
			jsonObj.remove("article");
			Article article = src.getArticle();
			JsonObject category = new JsonObject();
			category.addProperty("id", article.getStringId());
			category.addProperty("title", article.getArticle_title());
			category.addProperty("slug", article.getSlug());
			jsonObj.add("article", category);
		}

		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);

		return jsonObj;
	}
}