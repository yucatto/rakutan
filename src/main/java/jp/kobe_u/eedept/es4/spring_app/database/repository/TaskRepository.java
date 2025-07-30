package jp.kobe_u.eedept.es4.spring_app.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jp.kobe_u.eedept.es4.spring_app.database.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    // Additional query methods can be defined here if needed

}
