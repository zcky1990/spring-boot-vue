package app.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.FollowAuthors;

public interface FollowAuthorsRepository extends MongoRepository<FollowAuthors, String> {
	
	@Query(value = "{'users' : {'$ref' : 'users' , '$id' : ?0} }")
	List<FollowAuthors> findByUserId(ObjectId userId, Pageable pageable);
	
	@Query(value = "{'author' : {'$ref' : 'users' , '$id' : ?0} }")
	List<FollowAuthors> findByAuthorsId(ObjectId authorId);
	
	@Query(value = "{'users' : {'$ref' : 'users' , '$id' : ?0} , 'author' : {'$ref' : 'users' , '$id' : ?1} }")
	FollowAuthors findByUserIdAndAuhtorsId(ObjectId userId, ObjectId authorId );
}