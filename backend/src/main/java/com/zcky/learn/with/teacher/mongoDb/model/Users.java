package com.zcky.learn.with.teacher.mongoDb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Users {
	@Id
	private ObjectId _id;
	private String name;
	private String email;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String birthday;
	private boolean status;
	private String image_profile_url;
	private String modified_date;
	
	public String getStringId() {
		return _id.toHexString();
	}
	
	public ObjectId get_id() {
		return _id;
	}
	
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getImage_profile_url() {
		return image_profile_url;
	}

	public void setImage_profile_url(String image_profile_url) {
		this.image_profile_url = image_profile_url;
	}

	public String getModified_date() {
		return modified_date;
	}

	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	
	
	
}
