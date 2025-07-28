package jp.kobe_u.eedept.es4.spring_app.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.task.TaskRes;
import jp.kobe_u.eedept.es4.spring_app.service.TaskService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskRes createTask(@RequestBody TaskPostReq req) {
        return taskService.createTask(req);
    }

    @GetMapping
    public TaskRes getTask(@RequestBody TaskGetReq req) {
        return taskService.getTask(req);
    }

    @GetMapping("/all")
    public List<TaskRes> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping
    public TaskRes updateTask(@RequestBody TaskPutReq req) {
        return taskService.updateTask(req);
    }

    @DeleteMapping
    public void deleteTask(@RequestBody TaskDeleteReq req) {
        taskService.deleteTask(req);
    }
}
