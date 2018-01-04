package backend.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class AppUser {
    @Id
    private String id;
    private String username;
    private String password;
    private String name;
    private String fitbitToken;
    private ArrayList<Event> events;

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

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        //TODO: CHECK IF EVENT ALREADY EXITS
        events.add(event);
    }
}
