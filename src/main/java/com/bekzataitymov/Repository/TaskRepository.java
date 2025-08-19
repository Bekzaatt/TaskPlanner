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

    Optional<Task> findByUser(User user);

    @Query("select t from Task t where t.user.id = :id")
    List<Task> findAllByUserId(int id);

    @Transactional
    @Modifying
    @Query("delete from Task t where t.header = :header")
    void delete(String header);
}
