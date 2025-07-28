package jp.kobe_u.eedept.es4.spring_app.api.schema.response.task;

import java.time.LocalDateTime;

import jp.kobe_u.eedept.es4.spring_app.database.entities.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskRes {
    private Long taskId;
    private String taskName;
    private String taskType;
    private String tag;
    private String communityId;
    private String related_url;
    private LocalDateTime deadline;

    public TaskRes(Task task) {
        this.taskId = task.getTaskId();
        this.taskName = task.getTaskName();
        this.taskType = task.getTaskType();
        this.tag = task.getTag();
        this.communityId = task.getCommunityId();
        this.related_url = task.getRelated_url();
        this.deadline = task.getDeadline();
    }
}
