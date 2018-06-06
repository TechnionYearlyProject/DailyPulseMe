package backend.entity;

//Class used to send a List of users when getSubscribedUsers API call is received.
//other fields can be added here if necessary
public class Subscription {
    private String name;
    private String email;
    public Subscription(String email, String name){
        this.name = name;
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
