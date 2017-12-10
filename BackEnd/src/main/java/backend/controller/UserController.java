package backend.controller;

import backend.entity.Role;
import backend.entity.User;
import backend.entity.UserRegistration;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @GetMapping(value="/")
    public String index(){
        return "index";
    }
    @GetMapping(value = "/private")
    public String privateArea(){
        return "private";
    }
    @GetMapping(value="/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @RequestMapping(value="/register")
    public boolean registerUser(UserRegistration userRegistration){
        if(userService.getUser(userRegistration.getEmail()) != null)
            return false;
            userService.addUser(new User(userRegistration.getEmail(),userRegistration.getName(),userRegistration.getPassword(),new ArrayList<Role>()));
            return true;
    }
}

