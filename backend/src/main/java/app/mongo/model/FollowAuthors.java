package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;


public class FollowAuthors {
	@Id
	private ObjectId _id;

	@DBRef
	private Users author;
	
	@DBRef
	private Users users;
		
	public String getStringId() {
		return _id.toHexString();
	}

	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	
	public Users getAuthor() {
		return author;
	}

	public void setAuthor(Users author) {
		this.author = author;
	}

	public Users getUser() {
		return users;
	}
	public void setUser(Users user) {
		this.users = user;
	}


}
