package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.service.ProjectService;
import com.example.__2021142_2022002.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;

//Η κλάση είναι REST controller και όλα τα endpoints θα επιστρέφουν JSON
@Controller
//Ορίζει τη βασική διαδρομή για όλα τα endpoints ως projects
@RequestMapping("/projects")
//Ετικέτα για τεκμηρίωση
@Tag(name = "Projects")
public class ProjectController {


    //λογική για τα Projects
    private final ProjectService service;

    private final UserService userService;

    //Constructor injection
    public ProjectController(ProjectService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    // 1. REST endpoint για δημιουργία από JSON
    @PostMapping("/api")
    @PreAuthorize("hasRole('CREATOR')")
    @ResponseBody
    @Operation(
            summary = "Δημιουργία νέου έργου (JSON)",
            description = "Μόνο για χρήστες με ρόλο CREATOR. Δημιουργεί νέο έργο crowdfunding.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = Project.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Έργο δημιουργήθηκε", content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "403", description = "Δεν επιτρέπεται")
            }
    )
    public Project createJson(@RequestBody Project p, Principal me) {
        return service.create(p, me.getName());
    }


    // 2. HTML form: εμφάνιση της φόρμας
    @GetMapping("/create")
    @PreAuthorize("hasRole('CREATOR')")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        return "projectCreate";
    }

    // 3. HTML form: υποβολή νέου project
    @PostMapping("/create")
    @PreAuthorize("hasRole('CREATOR')")
    public String createProject(@ModelAttribute @Valid Project project,
                                BindingResult result,
                                Principal me) {
        if (result.hasErrors()) {
            return "projectCreate";
        }

        service.create(project, me.getName());
        return "redirect:/projects/creator/dashboard";
    }

    // 4. Εμφάνιση έργων του χρήστη (Creator Dashboard)
    @GetMapping("/creator/dashboard")
    @PreAuthorize("hasRole('CREATOR')")
    public String creatorDashboard(Model model, Principal principal) {
        String username = principal.getName();
        List<Project> myProjects = service.findByCreatorUsername(username);
        model.addAttribute("myProjects", myProjects);
        return "CreatorDashboard";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showProjectDetails(@PathVariable Long id, Model model, Principal principal) {
        Project project = service.findById(id); // ΠΡΕΠΕΙ να έχεις αυτή τη μέθοδο στο ProjectService
        model.addAttribute("project", project);

        // Υπολογισμός προόδου για το progress bar
        BigDecimal progress = project.getCollectedAmount()
                .divide(project.getTargetAmount(), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        model.addAttribute("progress", progress);

        // Προσθήκη του ρόλου για εμφάνιση των κουμπιών
        String role = userService.getRoleForUser(principal.getName());
        model.addAttribute("role", role);

        return "projectDetails"; // projectDetails.html
    }

    @GetMapping
    public String showPublicProjects(Model model, Principal principal) {
        List<Project> projects = service.publicList();
        model.addAttribute("projects", projects);

        if (principal != null) {
            String role = userService.getRoleForUser(principal.getName());
            model.addAttribute("role", role);
        }

        return "projectList";
    }

}


