package jp.kobe_u.eedept.es4.spring_app.database.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String taskName;
    private String taskType;
    private String tag;
    private String communityId;
    private String related_url;
    private LocalDateTime deadline;
}
