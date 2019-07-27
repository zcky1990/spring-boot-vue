package com.zcky.learn.with.teacher.environtment;

import com.zcky.learn.with.teacher.constant.Constant;

public class Environtment {
	private String environtment;
	
	public Environtment() {
		environtment = Constant.DEV_ENV;
	}

	public String getEnvirontment() {
		return environtment;
	}
}
