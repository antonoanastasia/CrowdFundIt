package com.example.__2021142_2022002.repo;

import com.example.__2021142_2022002.domain.ProjectUpdate;
import com.example.__2021142_2022002.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectUpdateRepository extends JpaRepository<ProjectUpdate, Long> {
    List<ProjectUpdate> findByProject(Project project);
}
