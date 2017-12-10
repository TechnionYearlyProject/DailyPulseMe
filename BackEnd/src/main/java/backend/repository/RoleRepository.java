package backend.repository;

import backend.entity.security.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String>{

}