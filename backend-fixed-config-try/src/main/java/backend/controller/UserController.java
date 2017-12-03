package backend.controller;

import backend.entity.Role;
import backend.entity.User;
import backend.entity.UserRegistration;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(value="/register")
    public String registerUser(@RequestBody UserRegistration userRegistration){
        if(!userRegistration.getPassword().equals(userRegistration.getPasswordConfirm()))
            return "Error the two passwords do not match";
        else if(userService.getUser(userRegistration.getEmail()) != null)
            return "Error this email already exists";
        else if(!validateEmail(userRegistration.getEmail()))
            return "Error email format is not correct";
        else{
            userService.addUser(new User(userRegistration.getEmail(),userRegistration.getName(),userRegistration.getPassword(),new ArrayList<Role>()));
            return "Success user registered";
        }
    }
}

