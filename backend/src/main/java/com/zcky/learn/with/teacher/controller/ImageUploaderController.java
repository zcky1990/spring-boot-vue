package com.zcky.learn.with.teacher.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.zcky.learn.with.teacher.model.UploadImage;
import com.zcky.learn.with.teacher.util.CloudinaryUtility;

@RestController
public class ImageUploaderController extends BaseController{
	
	@RequestMapping(value = "/api/upload_image", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String>  uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "content") String content, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		CloudinaryUtility util= new CloudinaryUtility();
		response= util.uploadImage(file, content);
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/upload_image_string", method = RequestMethod.POST)
	public ResponseEntity<String>  uploadImageFileString(@RequestBody UploadImage image, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		CloudinaryUtility util= new CloudinaryUtility();
		if(image.getContent().isEmpty()) {
			response= util.uploadBase64ImageString(image.getImage());
		}else {
			response= util.uploadBase64ImageString(image.getImage(), image.getContent());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}
