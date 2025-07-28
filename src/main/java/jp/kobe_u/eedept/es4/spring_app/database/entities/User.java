package jp.kobe_u.eedept.es4.spring_app.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    
        @Id
    private String userId;

    private String userName;
    
    private String email;
}
