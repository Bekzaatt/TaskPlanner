package com.bekzataitymov.Repository;

import com.bekzataitymov.Model.Task;
import com.bekzataitymov.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByHeader(String header);

    @Query("select new Task(t.id, t.header, t.description, t.user, t.isCompleted, t.timestamp) from Task t where t.user.id = :id")
    List<Task> findAllByUserId(int id);
    Optional<Task> findById(int id);
    void deleteById(int id);
}
