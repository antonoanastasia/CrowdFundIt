package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.domain.ProjectUpdate;
import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.repo.ProjectRepository;
import com.example.__2021142_2022002.repo.ProjectUpdateRepository;
import com.example.__2021142_2022002.service.ProjectUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/updates")
public class ProjectUpdateController {

    @Autowired
    private ProjectUpdateService service;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private ProjectUpdateRepository updateRepo;

    @PostMapping
    public ProjectUpdate createUpdate(@PathVariable Long projectId,
                                      @RequestBody String message,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        if (message == null || message.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message cannot be empty");
        }
        return service.addUpdate(projectId, userDetails.getUsername(), message.trim());
    }

    @GetMapping
    public List<ProjectUpdate> getUpdates(@PathVariable Long projectId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
        return updateRepo.findByProject(project);
    }
}
