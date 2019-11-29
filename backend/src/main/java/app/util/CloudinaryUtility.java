package app.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import app.config.CloudinaryConfig;
import app.config.CloudinaryEnv;
import app.config.CloudinaryModelJson;
import app.constants.Constant;

public class CloudinaryUtility {
	private Util util = new Util();
	private String env;
	private Gson gson = new Gson();
	private CloudinaryConfig config;
	private Cloudinary cloudinary;
	
	public CloudinaryUtility(String env) {
		this.env = env;
		this.getCloudinaryConfig();
		this.setProperties();
	}
	
	public void getCloudinaryConfig() {
		String stringConfig = util.getStringJsonFile(Constant.CLOUDINARY_CONFIG_PATH);
		CloudinaryModelJson cloudConfig = gson.fromJson(stringConfig, CloudinaryModelJson.class);
		ArrayList<CloudinaryEnv> environtmentConfig = cloudConfig.getArrayConfig();
		for(int i = 0; i < environtmentConfig.size(); i++) {
			CloudinaryEnv cloudinaryEnv = environtmentConfig.get(i);
			if(cloudinaryEnv.getEnv().equals(env)) {
				config = cloudinaryEnv.getConfig();
				break;
			}
		}
	}
	
	public void setProperties() {
		cloudinary = new Cloudinary(ObjectUtils.asMap(
				  "cloud_name", config.getCloud_name(),
				  "api_key", config.getApi_key(),
				  "api_secret", config.getApi_secret()));
	}
	
	public JsonObject uploadImage(String path) {
		File toUpload = new File(path);
		JsonObject response = new JsonObject();
		try {
			Map uploadResult = cloudinary.uploader().upload(toUpload, ObjectUtils.emptyMap());
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadBase64ImageString(String data) {
		JsonObject response = new JsonObject();
		try {
			Map uploadResult = cloudinary.uploader().upload(data, ObjectUtils.emptyMap());
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadBase64ImageString(String data, String folder) {
		JsonObject response = new JsonObject();
		try {
			Map options = ObjectUtils.asMap(
				    "folder", folder);
			Map uploadResult = cloudinary.uploader().upload(data, options);
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
		
	public JsonObject uploadImage(MultipartFile file) {
		JsonObject response = new JsonObject();
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadImage(MultipartFile file, String folder) {
		JsonObject response = new JsonObject();
		Map options;
		if(env.endsWith(Constant.DEV_ENV)) {
			options = ObjectUtils.asMap("folder", folder + "dev");
		}else {
			options = ObjectUtils.asMap("folder", folder);
		}
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}
	
	public JsonObject uploadImage(MultipartFile file, String folder, String name) {
		JsonObject response = new JsonObject();
		Map options;
		if(env.endsWith(Constant.DEV_ENV)) {
			options = ObjectUtils.asMap("folder", folder + "dev", "public_id", name);
		}else {
			options = ObjectUtils.asMap("folder", folder, "public_id", name);
		}
		try {
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
			response.addProperty("url", uploadResult.get("secure_url").toString());
			response.addProperty("status", "success");
		} catch (IOException e) {
			response.addProperty("error_message", e.getMessage().toString());
			response.addProperty("status", "error");
		}
		return response;
	}

}
