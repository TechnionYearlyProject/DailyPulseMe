package backend.entity;

import org.springframework.data.annotation.Id;

//import javax.persistence.Entity;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Role {

    @Id
    private String id;
    String name;

    Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
