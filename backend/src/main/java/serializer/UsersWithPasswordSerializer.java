package serializer;

import com.google.gson.*;

import mongo.model.Users;
import util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class UsersWithPasswordSerializer implements JsonSerializer<Users> {

	@Override
	public JsonElement serialize(Users src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String uid = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", uid);
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		return jsonObj;
	}
}
