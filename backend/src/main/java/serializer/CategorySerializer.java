package serializer;

import com.google.gson.*;

import mongo.model.Category;
import util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class CategorySerializer implements JsonSerializer<Category> {

	@Override
	public JsonElement serialize(Category src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		return jsonObj;
	}
}
