package com.zcky.learn.with.teacher.environtment;

import com.zcky.learn.with.teacher.constant.Constant;

public class Environtment {
	private int environtment;
	
	public Environtment() {
		environtment = Constant.DEV_ENV;
	}

	public int getEnvirontment() {
		return environtment;
	}
}
