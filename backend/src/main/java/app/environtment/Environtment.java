package app.environtment;

import app.constants.Constant;

public class Environtment {
	private String environtment;
	
	public Environtment() {
		environtment = Constant.PRODUCTION_ENV;
	}

	public String getEnvirontment() {
		return environtment;
	}
}
