package jp.kobe_u.eedept.es4.spring_app.api.schema.request.task;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskPutReq {
    private Long taskId;
    private String taskName;
    private String taskType;
    private String tag;
    private String communityId;
    private String related_url;
    private LocalDateTime deadline;
    private String userId;
    private Long companyId;
    private String status;
}
