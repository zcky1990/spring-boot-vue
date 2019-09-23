package app.serializer;

import com.google.gson.*;

import app.mongo.model.AccessLevel;

import java.lang.reflect.Type;

public class AccessLevelSerializer implements JsonSerializer<AccessLevel> {

	@Override
	public JsonElement serialize(AccessLevel src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		return jsonObj;
	}
}
