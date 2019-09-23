package app.model;

public class Order {
	private String direction;
	private String property;
	private Boolean ignoreCase;
	private String nullHandling;
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Boolean getIgnoreCase() {
		return ignoreCase;
	}

	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	public String getNullHandling() {
		return nullHandling;
	}

	public void setNullHandling(String nullHandling) {
		this.nullHandling = nullHandling;
	}

}
