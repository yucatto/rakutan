package jp.kobe_u.eedept.es4.spring_app.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId; //企業ID

    private String communityId; //コミュニティID 

    private String companyName; //企業名

    private String jobType; //職種

}