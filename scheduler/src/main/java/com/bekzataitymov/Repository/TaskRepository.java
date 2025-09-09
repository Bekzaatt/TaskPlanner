package com.bekzataitymov.Repository;

import com.bekzataitymov.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("select t.header, t.isCompleted, t.timestamp from Task t where t.user.id = :id")
    List<Task> findAllByUserId(int id);
}
