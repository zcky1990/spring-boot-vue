package com.zcky.learn.with.teacher.constant;

public class Constant {
	public static int PRODUCTION_ENV = 0;
	public static int STAGING_ENV = 1;
	public static int DEV_ENV = 2;
	
	public static int PAGE_SIZE = 25;

	public static String SUCCESS_RESPONSE_CODE ="200";
	public static String UNAUTHORIZED_RESPONSE_CODE = "401";
	public static String NOT_FOUND_RESPONSE_CODE = "404";
	public static String INTERNAL_SERVER_ERROR_RESPONSE_CODE = "500";
	
	public static String RESPONSE = "response";
	public static String ERROR_MESSAGE = "error_message";

	public static String SUCCESS = "success";
	public static String ERROR = "error"; 

	//Success Message
	public static String UPDATE_USER_PROFILE_SUCCESS_MESSAGE = "Success Update Profile";
	public static String SUCCESS_VALIDATED_USER_PROFILE_SUCCESS_MESSAGE = "Success validate Profile";
	public static String DELETE_USER_SUCCESS_MESSAGE = "Success Delete User";
	public static String UPDATE_ARTICLE_SUCCESS_MESSAGE = "Success Update Article";
	public static String DELETE_ARTICLE_SUCCESS_MESSAGE = "Success Delete Article";
	
	//Error Message
	public static String USER_NOT_VALIDATED_SUCCESS_MESSAGE = "User not validated please check your mail";
	public static String USER_ALREDY_EXISTS_ERROR_MESSAGE = "User Already Exists";
	public static String USER_NOT_FOUND_ERROR_MESSAGE = "User Not Found";
	public static String USER_FOUND_BUT_INACTIVE_ERROR_MESSAGE = "User Found But the account is inactive";

}
