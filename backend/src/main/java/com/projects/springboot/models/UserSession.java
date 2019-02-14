package com.projects.springboot.models;

public class UserSession {
	private boolean isActive;
	private String username;
	private String sessionId;
	
	public UserSession() {
		isActive = true;
		username = "test";
		sessionId = "sad1231b3jh2bj13hvjhd213";
	}
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
