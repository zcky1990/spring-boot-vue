package com.zcky.learn.with.teacher.mongoDb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class Roles {
	@Id
	private ObjectId _id;
	
	private String name;
	private Boolean status;
	
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
