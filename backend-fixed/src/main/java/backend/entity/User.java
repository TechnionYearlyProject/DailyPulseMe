package backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import javax.persistence.Entity;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
public class User{

    @Id
    private String id;
    private String name;
    private String email;
    private List<Role> roles;
    @JsonIgnore
    private String password;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> userRoles) {
        this.roles = userRoles;
    }

    public User(String email, String name, String password, List<Role> roles) {
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.name = name;
    }

    User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
