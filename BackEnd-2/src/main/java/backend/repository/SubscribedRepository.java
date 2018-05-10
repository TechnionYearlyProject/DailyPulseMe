package backend.repository;

import backend.entity.AppUser;
import backend.entity.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

//import org.springframework.data.jpa.repository.JpaRepository;
public interface SubscribedRepository extends MongoRepository<Subscription,String> {
    //email == username
    AppUser findByEmail(@Param("email") String email);
    void deleteByEmail(@Param("email") String email);
}