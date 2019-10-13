package app.serializer;

import com.google.gson.*;

import app.mongo.model.Article;
import app.mongo.model.BookmarksArticle;
import app.mongo.model.Category;
import app.mongo.model.Users;
import app.util.TimeUtility;

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
			if(user.getFirstname()!= null) {
				userJson.addProperty("firstname", user.getFirstname());
			}
			if(user.getLastname()!= null) {
				userJson.addProperty("lastname", user.getLastname());
			}
			if(user.getEmail()!= null) {
				userJson.addProperty("email", user.getEmail());
			}
			if(user.getUsername()!= null) {
				userJson.addProperty("username", user.getUsername());
			}
			if(user.getName()!= null) {
				userJson.addProperty("name", user.getName());
			}
			if(user.getImageUrl()!= null) {
				userJson.addProperty("image_profile_url", user.getImageUrl());
			}
			jsonObj.add("users", userJson);
		}

		if(jsonObj.has("article")) {
			jsonObj.remove("article");
			Article article = src.getArticle();
			JsonObject category = new JsonObject();
			category.addProperty("id", article.getStringId());
			if(article.getArticle_title() != null) {
				category.addProperty("title", article.getArticle_title());
			}
			if(article.getSlug() != null) {
				category.addProperty("title", article.getSlug());
			}
			if(article.getArticle_short_content() != null) {
				category.addProperty("title", article.getArticle_short_content());
			}
			JsonArray listCategory = new JsonArray();
			if(article.getCategoryArticle() != null) {
				for(int i = 0; i< article.getCategoryArticle().size(); i++) {
					Category asd = article.getCategoryArticle().get(i);
					listCategory.add(asd.getName());
				}
			}
			category.add("category",listCategory );
			jsonObj.add("article", category);
		}
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);

		return jsonObj;
	}
}
