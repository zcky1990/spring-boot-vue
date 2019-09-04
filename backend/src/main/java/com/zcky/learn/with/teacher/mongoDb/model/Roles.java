package com.zcky.learn.with.teacher.mongoDb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.zcky.learn.with.teacher.model.request.RolesRequest;

public class Roles {
	@Id
	private ObjectId _id;
	private String name;
	private String type;
	private Boolean status;
	@DBRef
	private AccessLevel access_level;
	
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
	public AccessLevel getAccess_level() {
		return access_level;
	}
	public void setAccess_level(AccessLevel access_level) {
		this.access_level = access_level;
	}
	public void fromObject(RolesRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setName(request.getName());
		this.setType(request.getType());
		this.setStatus(request.getStatus());
		AccessLevel level = new AccessLevel();
		level.set_id(new ObjectId(request.getAccess_level().getId()));
		this.setAccess_level(level);
	}
}
