package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.service.ProjectService;
import com.example.__2021142_2022002.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    private final ProjectService projects;
    private final UserService users;

    public AdminPageController(ProjectService projects, UserService users) {
        this.projects = projects;
        this.users = users;
    }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        model.addAttribute("pendingProjects", projects.pending());
        model.addAttribute("allUsers", users.listUsers());
        return "AdminDashboard"; // templates/AdminDashboard.html
    }
}
