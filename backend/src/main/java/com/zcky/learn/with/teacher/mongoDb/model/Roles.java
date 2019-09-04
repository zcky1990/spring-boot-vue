package com.zcky.learn.with.teacher.mongoDb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.zcky.learn.with.teacher.model.request.RolesRequest;

public class Roles {
	@Id
	private ObjectId _id;
	private String name;
	private String type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public void fromObject(RolesRequest request) {
		this.set_id(new ObjectId(request.getId()));
		this.setName(request.getName());
		this.setType(request.getType());
		this.setStatus(request.getStatus());
	}
}
