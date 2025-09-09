package com.example.__2021142_2022002.service;

import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.domain.ProjectUpdate;
import com.example.__2021142_2022002.repo.ProjectRepository;
import com.example.__2021142_2022002.repo.ProjectUpdateRepository;
import com.example.__2021142_2022002.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProjectUpdateService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectUpdateRepository updateRepository;

    public ProjectUpdate addUpdate(Long projectId, String username, String message) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        if (!project.getCreator().getUsername().equals(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the creator can post updates.");
        }

        ProjectUpdate update = new ProjectUpdate();
        update.setAuthor(user);
        update.setProject(project);
        update.setMessage(message);
        return updateRepository.save(update);
    }
}
