package com.zcky.learn.with.teacher.mongoDb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.zcky.learn.with.teacher.model.request.AccessLevelRequest;
import com.zcky.learn.with.teacher.model.request.RolesRequest;

public class AccessLevel {
	@Id
	private ObjectId _id;
	private Integer level;
	private String description;
	@DBRef
	private Roles role;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getStringId() {
		return _id.toHexString();
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public void fromObject(AccessLevelRequest request) {
		this.set_id(new ObjectId(request.getId()));
		this.setDescription(request.getDescription());
		this.setLevel(request.getLevel());
		Roles role = new Roles();
		role.set_id(new ObjectId(request.getRole_id()));
		this.setRole(role);
	}
}
