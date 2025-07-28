package jp.kobe_u.eedept.es4.spring_app.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Community {

    @Id
    private String communityId;

    private String communityName;
}
