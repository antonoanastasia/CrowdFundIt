package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.service.UserService;
import org.springframework.ui.Model;
import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name="Admin", description = "Ενέργειες διαχειριστή (έγκριση/απόρριψη έργων")
public class AdminController {

    private final ProjectService projects;

    private final UserService userService;


    // Χειροκίνητος constructor (αντί για Lombok @RequiredArgsConstructor)
    public AdminController(ProjectService projects, UserService userService) {
        this.projects = projects;
        this.userService = userService;
    }

    @Operation(
            summary = "Λίστα έργων σε εκκρεμότητα",
            description = "Επιστρέφει όλα τα έργα που δεν έχουν εγκριθεί ακόμη (status = PENDING)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Επιτυχής επιστροφή έργων", content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "403", description = "Μη εξουσιοδοτημένος χρήστης")
            }
    )

    /** GET /admin/pending-projects */
    @GetMapping("/pending-projects")
    public List<Project> pending() { return projects.pending(); }

    @GetMapping("/admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        model.addAttribute("pendingProjects", projects.pending());  // έργα σε εκκρεμότητα
        model.addAttribute("allUsers", userService.listUsers());   // Όλοι οι χρήστες
        return "AdminDashboard"; // Το AdminDashboard.html από /resources/templates
    }


    @Operation(
            summary = "Έγκριση έργου",
            description = "Αλλάζει την κατάσταση του έργου σε APPROVED",
            parameters = {
                    @Parameter(name = "id", description = "ID του έργου προς έγκριση", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Το έργο εγκρίθηκε", content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "404", description = "Το έργο δεν βρέθηκε")
            }
    )

    /** PUT /admin/approve/{id} */
    @PutMapping("/approve/{id}")
    public Project approve(@PathVariable Long id) { return projects.approve(id); }

    @Operation(
            summary = "Απόρριψη έργου",
            description = "Αλλάζει την κατάσταση του έργου σε REJECTED",
            parameters = {
                    @Parameter(name = "id", description = "ID του έργου προς απόρριψη", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Το έργο απορρίφθηκε", content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "404", description = "Το έργο δεν βρέθηκε")
            }
    )

    /** PUT /admin/reject/{id} */
    @PutMapping("/reject/{id}")
    public Project reject(@PathVariable Long id) { return projects.reject(id); }
}
