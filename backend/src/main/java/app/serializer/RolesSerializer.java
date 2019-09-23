package app.serializer;

import com.google.gson.*;

import app.mongo.model.AccessLevel;
import app.mongo.model.Roles;
import app.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class RolesSerializer implements JsonSerializer<Roles> {

	@Override
	public JsonElement serialize(Roles src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		AccessLevel access = src.getAccess_level();
		String accessId = access.getStringId();
		if(jsonObj.has("access_level")) {
			jsonObj.remove("access_level");
			JsonObject access_level = new JsonObject();
			access_level.addProperty("id", accessId);
			access_level.addProperty("description", access.getDescription());
			access_level.addProperty("level", access.getLevel());
			jsonObj.add("access_level", access_level);
		}
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		return jsonObj;
	}
}
