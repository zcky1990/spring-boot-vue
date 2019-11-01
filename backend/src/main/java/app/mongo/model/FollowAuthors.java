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
	private Users user;
	
	public FollowAuthors(ObjectId userId, ObjectId authorId) {
		user = new Users();
		user.set_id(userId);
		author = new Users();
		author.set_id(authorId);
	}
	
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
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}


}
