package backend.controller;

import backend.entity.AppUser;
import backend.entity.StringDummy;
import backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static backend.security.SecurityConstants.SECRET;
import static backend.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp( AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }
    @GetMapping("/private")
    public String privatee(){
        return "THIS IS PRIVATE!!";
    }
    @PostMapping("/userPassword")
    public String getPassword(@RequestBody String username){
        System.out.println("----"+username+"----");
        if(applicationUserRepository.findByUsername(username)==null){
            return "DOES NOT EXIST";
        } else return applicationUserRepository.findByUsername(username).getPassword();
    }
}