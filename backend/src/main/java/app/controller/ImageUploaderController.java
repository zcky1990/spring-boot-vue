package app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import app.environtment.EnvironmentBuild;
import app.model.UploadImage;
import app.util.CloudinaryUtility;

@RestController
@RequestMapping("/api")
public class ImageUploaderController extends BaseController{
	
	@Autowired
	private EnvironmentBuild env;
	
	@RequestMapping(value = "/upload_image", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String>  uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "content") String content, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		CloudinaryUtility util= new CloudinaryUtility(env.getEnvirontment());
		response= util.uploadImage(file, content);
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/upload_image_string", method = RequestMethod.POST)
	public ResponseEntity<String>  uploadImageFileString(@RequestBody UploadImage image, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		CloudinaryUtility util= new CloudinaryUtility(env.getEnvirontment());
		if(image.getContent().isEmpty()) {
			response= util.uploadBase64ImageString(image.getImage());
		}else {
			response= util.uploadBase64ImageString(image.getImage(), image.getContent());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}
