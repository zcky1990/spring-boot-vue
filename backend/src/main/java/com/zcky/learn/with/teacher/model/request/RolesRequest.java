package com.zcky.learn.with.teacher.model.request;

public class RolesRequest {
	private String id;
	private String name;
	private String type;
	private Boolean status;
	private AccessLevelRequest access_level;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public AccessLevelRequest getAccess_level() {
		return access_level;
	}
	public void setAccess_level(AccessLevelRequest access_level) {
		this.access_level = access_level;
	}
}
