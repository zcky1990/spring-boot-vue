package app.serializer;

import com.google.gson.*;

import app.model.response.ArticleResponse;
import app.mongo.model.BookmarksArticle;
import app.mongo.model.Category;
import app.mongo.model.Users;
import app.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ArticleBookmarkSerializer implements JsonSerializer<ArticleResponse> {
	
	@Override
	public JsonElement serialize(ArticleResponse src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		
		
		if(jsonObj.has("author")) {
			jsonObj.remove("author");
			Users user = src.getAuthor();
			JsonObject userJson = new JsonObject();
			userJson.addProperty("id", user.getStringId());
			userJson.addProperty("firstname", user.getFirstname());
			userJson.addProperty("lastname", user.getLastname());
			userJson.addProperty("email", user.getEmail());
			userJson.addProperty("username", user.getUsername());
			userJson.addProperty("name", user.getName());
			userJson.addProperty("image_profile_url", user.getImageUrl());
			jsonObj.add("author", userJson);
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
				
		if(jsonObj.has("bookmark")) {
			jsonObj.remove("bookmark");
			BookmarksArticle bookmarksArticle = src.getBookmark();
			JsonObject bookmark = new JsonObject();
			bookmark.addProperty("id",bookmarksArticle.getStringId());
			bookmark.addProperty("userId",bookmarksArticle.getUser().getStringId());
			bookmark.addProperty("articleId",bookmarksArticle.getArticle().getStringId());
			jsonObj.add("bookmark", bookmark);
		}
				
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		
		return jsonObj;
	}
}
