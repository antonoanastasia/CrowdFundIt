package com.example.__2021142_2022002.service;

import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.domain.ProjectStatus;
import com.example.__2021142_2022002.repo.ProjectRepository;
import com.example.__2021142_2022002.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repo; // ΠΡΟΣΟΧΗ: όχι final, όχι lombok

    @Autowired
    private UserRepository users;

    @Transactional
    public Project create(Project p, String creatorUsername) {
        p.setId(null);
        p.setStatus(ProjectStatus.PENDING);

        if (p.getCollectedAmount() == null) {
            p.setCollectedAmount(BigDecimal.ZERO);
        }

        User creator = users.findByUsername(creatorUsername).orElseThrow();
        p.setCreator(creator);
        return repo.save(p);
    }

    public List<Project> publicList() {
        return repo.findAll().stream()
                .filter(pr -> pr.getStatus() == ProjectStatus.FUNDING || pr.getStatus() == ProjectStatus.COMPLETED)
                .toList();
    }

    public List<Project> pending() {
        return repo.findByStatus(ProjectStatus.PENDING);
    }

    @Transactional
    public Project approve(Long id) {
        Project p = repo.findById(id).orElseThrow();
        if (p.getStatus() != ProjectStatus.PENDING) throw new IllegalStateException("Not pending");
        p.setStatus(ProjectStatus.FUNDING);
        return p;
    }

    @Transactional
    public Project reject(Long id) {
        Project p = repo.findById(id).orElseThrow();
        if (p.getStatus() != ProjectStatus.PENDING) throw new IllegalStateException("Not pending");
        p.setStatus(ProjectStatus.REJECTED);
        return p;
    }

    public List<Project> findByCreatorUsername(String username) {
        return repo.findByCreator_Username(username);
    }

    public Project findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

}
