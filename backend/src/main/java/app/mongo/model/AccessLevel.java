package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import app.model.request.AccessLevelRequest;

public class AccessLevel {
	@Id
	private ObjectId _id;
	private Integer level;
	private String description;
	
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
	public void fromObject(AccessLevelRequest request) {
		this.set_id(new ObjectId(request.getId()));
		this.setDescription(request.getDescription());
		this.setLevel(request.getLevel());
	}
}
