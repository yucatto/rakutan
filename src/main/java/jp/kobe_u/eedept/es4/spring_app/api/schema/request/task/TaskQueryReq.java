package jp.kobe_u.eedept.es4.spring_app.api.schema.request.task;

import lombok.Data;

@Data
public class TaskQueryReq {
    private Long companyId;
    private String communityId;
    private String userId;
}
