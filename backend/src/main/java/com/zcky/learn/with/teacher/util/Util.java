package com.zcky.learn.with.teacher.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.util.ResourceUtils;

public class Util {
	
	
	public String getJsonFile(String path) {
		File file = null;
		String content = "";
		try {
			file = ResourceUtils.getFile("classpath:"+path);
			content = new String(Files.readAllBytes(file.toPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

}
