package com.zcky.learn.with.teacher.mongoDb.serializer;

import com.google.gson.*;
import com.zcky.learn.with.teacher.mongoDb.model.Article;
import com.zcky.learn.with.teacher.mongoDb.model.Category;
import com.zcky.learn.with.teacher.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ArticleListSerializer implements JsonSerializer<Article> {

	@Override
	public JsonElement serialize(Article src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);


		if(jsonObj.has("author")) {
			jsonObj.remove("author");
			/*Users user = src.getAuthor();
			JsonObject userJson = new JsonObject();
			userJson.addProperty("id", user.getStringId());
			userJson.addProperty("firstname", user.getFirstname());
			userJson.addProperty("lastname", user.getLastname());
			userJson.addProperty("email", user.getEmail());
			userJson.addProperty("username", user.getUsername());
			userJson.addProperty("name", user.getName());
			userJson.addProperty("image_profile_url", user.getImageUrl());
			jsonObj.add("author", userJson);*/
		}

		String articleContent = src.getArticle_content();
		String image = "";
		if(articleContent.contains("<figure")) {
			int start = articleContent.indexOf("<figure");
			int end = articleContent.indexOf("</figure>")+9;
			image = articleContent.substring(start, end);
			articleContent = articleContent.replace(image, "");

			int index = image.indexOf("src=")+5;
			int endIndex = 0;
			if(image.contains("<figcaption>")) {
				endIndex = image.indexOf("<figcaption>")-2;
			}else {
				endIndex = image.indexOf("</figure>")-2;
			}
			String imgSrc =image.substring(index, endIndex);
			jsonObj.addProperty("imageHeader", imgSrc);
			String lazyImage = imgSrc.replace("/upload/", "/upload/f_auto,q_auto,e_blur:300/");
			jsonObj.addProperty("imageHeaderLazy", lazyImage);

		}

		if(articleContent.length() > 200) {
			articleContent = articleContent.substring(0, 200)+"...";
		}

		if(articleContent.contains("<strong>")) {
			articleContent.replaceAll("<strong>", "");
			articleContent.replaceAll("</strong>", "");
		}

		if(articleContent.contains("<h2")) {
			int start = articleContent.indexOf("<h2");
			int end = articleContent.indexOf("</h2>")+5;
			String subtitle = articleContent.substring(start, end);
			articleContent = articleContent.replace(subtitle, "");
		}
		jsonObj.remove("article_content");
		if(!articleContent.isEmpty()) {
			jsonObj.addProperty("article_content", articleContent);
		}

		if(jsonObj.has("categoryArticle")) {
			jsonObj.remove("categoryArticle");
			List<Category> list = src.getCategoryArticle();
			JsonArray listCategory = new JsonArray();
			for(int i = 0 ; i < list.size(); i++) {
				Category categoryArticle = list.get(i);
				JsonObject category = new JsonObject();
				category.addProperty("id", categoryArticle.getStringId());
				category.addProperty("name", categoryArticle.getName());
				category.addProperty("status", categoryArticle.getStatus());
				category.addProperty("type", categoryArticle.getType());
				listCategory.add(category);
			}
			jsonObj.add("categoryArticle", listCategory);
		}

		if(jsonObj.has("reference_list")) {
			jsonObj.remove("reference_list");
		}

		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);

		return jsonObj;
	}
}
