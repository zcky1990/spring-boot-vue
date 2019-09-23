package repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mongo.model.Roles;

public interface RolesRepository extends MongoRepository<Roles, String> {
	Roles findBy_id(ObjectId _id);

	@Query(value = "{ 'status': ?0}")
	List<Roles> findAllByStatus(Boolean status, Pageable pageable);

	@Query(value = "{ 'status': ?0}")
	List<Roles> findAllByStatus(Boolean status);
	
	@Query(value = "{'name' : ?0 }")
	Roles findByName(String name);
}
