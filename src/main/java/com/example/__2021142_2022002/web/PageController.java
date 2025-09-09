package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.web.dto.ProjectSearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Home
    @GetMapping({"/", "/home", "/home.html"})
    public String home(Model model) {
        // για τα th:field="*{...}" στο home.html
        model.addAttribute("search", new ProjectSearchForm());
        return "home"; // templates/home.html
    }

    // Login
    @GetMapping({"/login", "/login.html"})
    public String login() {
        return "login"; // templates/login.html
    }

    // Register
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // έχει πεδία username, password, role
        return "register"; // templates/register.html
    }

    // Error
    @GetMapping({"/error", "/error.html"})
    public String error() {
        return "error"; // templates/error.html
    }

    // Admin Dashboard
    @GetMapping({"/AdminDashboard", "/AdminDashboard.html"})
    public String adminDashboard() {
        return "AdminDashboard"; // templates/AdminDashboard.html
    }

    // Backer Dashboard
    @GetMapping({"/BackerDashboard", "/BackerDashboard.html"})
    public String backerDashboard() {
        return "BackerDashboard"; // templates/BackerDashboard.html
    }

    // Creator Dashboard
    @GetMapping({"/CreatorDashboard", "/CreatorDashboard.html"})
    public String creatorDashboard() {
        return "CreatorDashboard"; // templates/CreatorDashboard.html
    }

    // Project List
    @GetMapping({"/projectList", "/projectList.html"})
    public String projectList() {
        return "projectList"; // templates/projectList.html
    }

    // Project Details
    @GetMapping({"/projectDetails", "/projectDetails.html"})
    public String projectDetails() {
        return "projectDetails"; // templates/projectDetails.html
    }

    // Project Create
    @GetMapping({"/projectCreate", "/projectCreate.html"})
    public String projectCreate() {
        return "projectCreate"; // templates/projectCreate.html
    }

    // Project Pending
    @GetMapping({"/projectPending", "/projectPending.html"})
    public String projectPending() {
        return "projectPending"; // templates/projectPending.html
    }
}
