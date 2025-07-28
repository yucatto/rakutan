package jp.kobe_u.eedept.es4.spring_app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.task.TaskRes;
import jp.kobe_u.eedept.es4.spring_app.database.entities.Task;
import jp.kobe_u.eedept.es4.spring_app.database.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    // Create
    public TaskRes createTask(TaskPostReq req) {
        Task task = new Task();
        task.setTaskName(req.getTaskName());
        task.setTaskType(req.getTaskType());
        task.setTag(req.getTag());
        task.setCommunityId(req.getCommunityId());
        task.setRelated_url(req.getRelated_url());
        task.setDeadline(req.getDeadline());
        task = taskRepository.save(task);
        return new TaskRes(task);
    }

    // Read (Single)
    public TaskRes getTask(TaskGetReq req) {
        Task task = taskRepository.findById(req.getTaskId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Task not found with ID: " + req.getTaskId()));
        return new TaskRes(task);
    }

    // Read (All)
    public List<TaskRes> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskRes::new)
                .collect(Collectors.toList());
    }

    // Update
    public TaskRes updateTask(TaskPutReq req) {
        Long taskId = req.getTaskId();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));

        task.setTaskName(req.getTaskName());
        task.setTaskType(req.getTaskType());
        task.setTag(req.getTag());
        task.setCommunityId(req.getCommunityId());
        task.setRelated_url(req.getRelated_url());
        task.setDeadline(req.getDeadline());
        task = taskRepository.save(task);
        return new TaskRes(task);
    }

    // Delete
    public void deleteTask(TaskDeleteReq req) {
        Long taskId = req.getTaskId();
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task not found with ID: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }
}
