package org.example.datahub.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByCreatorIdAndStatus(Long creatorId, String status, Pageable pageable);

    Page<Task> findAllByCreatorIdAndStatusAndDeptIdIn(Long creatorId, String status, List<Long> deptIds, Pageable pageable);

    Page<Task> findAllByCreatorId(Long creatorId, Pageable pageable);

    Page<Task> findAllByCreatorIdAndDeptIdIn(Long creatorId, List<Long> deptIds, Pageable pageable);
}