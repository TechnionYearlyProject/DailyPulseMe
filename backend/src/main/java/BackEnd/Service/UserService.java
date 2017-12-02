package backend.service;

import backend.entity.User;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void addUser(User user){
        userRepository.save(user);
    }
    public User getUser(String email){
        return userRepository.findByEmail(email);
    }
}
