package com.example.__2021142_2022002.repo;

import com.example.__2021142_2022002.domain.Funding;
import com.example.__2021142_2022002.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface FundingRepository extends JpaRepository<Funding, Long> {
    List<Funding> findByProject(Project project);
}

