package com.zcky.learn.with.teacher.mongoDb.serializer;

import com.google.gson.*;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;

public class UsersAdminSerializer implements JsonSerializer<Users> {

	@Override
	public JsonElement serialize(Users src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String uid = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}

		if(jsonObj.has("roles")) {
			JsonObject role = new JsonObject();
			role.addProperty("id", src.getRoles().getStringId());
			role.addProperty("name", src.getRoles().getName());
			role.addProperty("type", src.getRoles().getType());
			role.addProperty("status", src.getRoles().getStatus());
			
			JsonObject accessLevel = new JsonObject();
			accessLevel.addProperty("id", src.getRoles().getAccess_level().getStringId());
			accessLevel.addProperty("level", src.getRoles().getAccess_level().getLevel());
			accessLevel.addProperty("description", src.getRoles().getAccess_level().getDescription());
			
			role.add("access_level", accessLevel);
			
			jsonObj.add("roles", role);
		}
		jsonObj.addProperty("id", uid);
		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);
		return jsonObj;
	}
}
