package backend.controller;

import backend.entity.security.Role;
import backend.entity.User;
import backend.entity.UserRegistration;
import backend.entity.security.UserRole;
import backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @PostMapping("/login")
    public String login() {
        return "login...";
    }

    @PostMapping(value="/register")
    public boolean registerUser(@RequestBody User user){

        if(userService.getUser(user.getEmail()) != null) {
            return false;
        }

        Set<UserRole> userRoles = new HashSet<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);
        return true;
    }
}

