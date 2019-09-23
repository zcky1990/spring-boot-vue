package environtment;

import constants.Constant;

public class Environtment {
	private String environtment;
	
	public Environtment() {
		environtment = Constant.DEV_ENV;
	}

	public String getEnvirontment() {
		return environtment;
	}
}
