package com.zcky.learn.with.teacher.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.environtment.Environtment;

import config.CloudinaryModelJson;
import config.CloudinaryConfig;
import config.CloudinaryEnv;

public class CloudinaryUtility {
	private Util util = new Util();
	private Environtment env = new Environtment();
	private Gson gson = new Gson();
	private CloudinaryConfig config;
	private Cloudinary cloudinary;
	
	public CloudinaryUtility() {
		this.getCloudinaryConfig();
		this.setProperties();
	}
	
	
	public void getCloudinaryConfig() {
		String stringConfig = util.getJsonFile(Constant.CLOUDINARY_CONFIG_PATH);
		CloudinaryModelJson cloudConfig = gson.fromJson(stringConfig, CloudinaryModelJson.class);
		ArrayList<CloudinaryEnv> environtmentConfig = cloudConfig.getconfig();
		for(int i = 0; i < environtmentConfig.size(); i++) {
			CloudinaryEnv cloudinaryEnv = environtmentConfig.get(i);
			if(cloudinaryEnv.getEnv().equals(env.getEnvirontment())) {
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

}