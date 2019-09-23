package app.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.AccessLevel;

public interface AccessLevelRepository extends MongoRepository<AccessLevel, String> {
	@Query(value = "{ 'role': {'$ref' : 'role' , '$id' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleId(ObjectId _Id);
	
	@Query(value = "{ 'role': {'$ref' : 'role' , '$id' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleId(ObjectId _Id, Pageable pageable);
}
