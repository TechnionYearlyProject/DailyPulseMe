package backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AppUser {


    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String fitbitToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getFitbitToken() {
        return fitbitToken;
    }

    public void setFitbitToken(String fitbitToken) {
        this.fitbitToken = fitbitToken;
    }
}