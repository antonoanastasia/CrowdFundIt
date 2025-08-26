package com.example.__2021142_2022002.repo;

import com.example.__2021142_2022002.domain.Funding;
import com.example.__2021142_2022002.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    List<Funding> findByProject(Project project);
}

