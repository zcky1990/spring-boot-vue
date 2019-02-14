package com.projects.springboot.environtment;

import com.projects.springboot.constants.Constants;

public class Environtment {
	private String env = Constants.DEV;

	public  String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}
}
