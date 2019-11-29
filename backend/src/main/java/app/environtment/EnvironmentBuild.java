package app.environtment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentBuild {
	@Value("${build.env}")	
	private String environtment;
	
	public String getEnvirontment() {
		return environtment;
	}

}
