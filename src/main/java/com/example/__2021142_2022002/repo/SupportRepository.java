package com.example.__2021142_2022002.repo;


import com.example.__2021142_2022002.domain.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findByBackerUsername(String username);
}