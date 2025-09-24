package com.example.__2021142_2022002.repo;


import com.example.__2021142_2022002.domain.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findByBackerUsername(String username);
}