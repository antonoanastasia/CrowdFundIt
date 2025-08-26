package com.example.__2021142_2022002.repo;

import com.example.__2021142_2022002.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
import java.util.*;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByCreator_Username(String username);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select p from Project p where p.id = :id")
    Optional<Project> findWithLock(@Param("id") Long id);
}
