package jp.kobe_u.eedept.es4.spring_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskDeleteReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskGetReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskPostReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskPutReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.request.task.TaskQueryReq;
import jp.kobe_u.eedept.es4.spring_app.api.schema.response.task.TaskRes;
import jp.kobe_u.eedept.es4.spring_app.database.entities.Task;
import jp.kobe_u.eedept.es4.spring_app.database.repository.TaskRepository;
import jp.kobe_u.eedept.es4.spring_app.exception.ResourceNotFoundException;
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
        task.setUserId(req.getUserId());
        task.setCompanyId(req.getCompanyId());
        task.setStatus("open"); // Default status can be set here
        task = taskRepository.save(task);
        return new TaskRes(task);
    }

    // Read (Single)
    public TaskRes getTask(TaskGetReq req) {
        Task task = taskRepository.findById(req.getTaskId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Task not found with ID: " + req.getTaskId()));
        return new TaskRes(task);
    }

    // Read (All)
    public List<TaskRes> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(TaskRes::new)
                .collect(Collectors.toList());
    }

    // Read (Query)
    public List<TaskRes> getTasksByQuery(TaskQueryReq req) {
        Long companyId = req.getCompanyId();
        String communityId = req.getCommunityId();
        String userId = req.getUserId();
        Specification<Task> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (companyId != null) {
                predicates.add(cb.equal(root.get("companyId"), companyId));
            }
            if (communityId != null) {
                predicates.add(cb.equal(root.get("communityId"), communityId));
            }
            if (userId != null) {
                predicates.add(cb.equal(root.get("userId"), userId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        List<Task> tasks = taskRepository.findAll(spec);
        return tasks.stream()
                .map(TaskRes::new)
                .collect(Collectors.toList());
    }

    // Update
    public TaskRes updateTask(TaskPutReq req) {
        Long taskId = req.getTaskId();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

        task.setTaskName(req.getTaskName());
        task.setTaskType(req.getTaskType());
        task.setTag(req.getTag());
        task.setCommunityId(req.getCommunityId());
        task.setRelated_url(req.getRelated_url());
        task.setDeadline(req.getDeadline());
        task.setUserId(req.getUserId());
        task.setCompanyId(req.getCompanyId());
        task.setStatus(req.getStatus());
        task = taskRepository.save(task);
        return new TaskRes(task);
    }

    // Delete
    public void deleteTask(TaskDeleteReq req) {
        Long taskId = req.getTaskId();
        if (!taskRepository.existsById(taskId)) {
            throw new ResourceNotFoundException("Task not found with ID: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }
}
