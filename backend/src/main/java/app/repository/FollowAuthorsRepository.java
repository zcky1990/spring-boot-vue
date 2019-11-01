package app.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.FollowAuthors;

public interface FollowAuthorsRepository extends MongoRepository<FollowAuthors, String> {
	FollowAuthors findBy_id(ObjectId _id);

	@Query(value = "{'user' : {'$ref' : 'users' , '$id' : ?0} }")
	List<FollowAuthors> findByUserId(ObjectId userId);
	
	@Query(value = "{'author' : {'$ref' : 'users' , '$id' : ?0} }")
	List<FollowAuthors> findByAuthorsId(ObjectId authorId);
	
	@Query(value = "{'user' : {'$ref' : 'users' , '$id' : ?0} , 'author' : {'$ref' : 'users' , '$id' : ?1} }")
	FollowAuthors findByUserIdAndAuhtorsId(ObjectId userId, ObjectId authorId );
}