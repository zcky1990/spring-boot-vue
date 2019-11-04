package app.serializer;

import com.google.gson.*;

import app.mongo.model.FollowAuthors;
import app.mongo.model.Users;

import java.lang.reflect.Type;

public class UserFollowSerializer implements JsonSerializer<FollowAuthors> {

	@Override
	public JsonElement serialize(FollowAuthors src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObj = (JsonObject) new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		Users authors = src.getAuthor();
		if (jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		if (jsonObj.has("author")) {
			JsonObject author = jsonObj.getAsJsonObject("author");
			author.remove("password");
			author.remove("_id");
			author.addProperty("id", authors.getStringId());
		}
		
		if (jsonObj.has("users")) {
			jsonObj.remove("users");
		}

		jsonObj.addProperty("id", id);
		return jsonObj;
	}
}
