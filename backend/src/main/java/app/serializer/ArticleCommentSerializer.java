package app.serializer;

import com.google.gson.*;

import app.mongo.model.Article;
import app.mongo.model.ArticleComment;
import app.mongo.model.Users;
import app.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class ArticleCommentSerializer implements JsonSerializer<ArticleComment> {
	
	@Override
	public JsonElement serialize(ArticleComment src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		
		Users user = src.getAuthor();
		if(jsonObj.has("user")) {
			jsonObj.remove("user");
		}
		
		Article article = src.getArticle();
		if(jsonObj.has("article")) {
			jsonObj.remove("article");
		}
				
		JsonObject userJson = new JsonObject();
		userJson.addProperty("id", user.getStringId());
		userJson.addProperty("firstname", user.getFirstname());
		userJson.addProperty("lastname", user.getLastname());
		userJson.addProperty("email", user.getEmail());
		userJson.addProperty("username", user.getUsername());
		userJson.addProperty("name", user.getName());
		userJson.addProperty("image_profile_url", user.getImageUrl());
		
		jsonObj.add("users", userJson);
		jsonObj.addProperty("article_id", article.getStringId());
		
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		
		return jsonObj;
	}
}
