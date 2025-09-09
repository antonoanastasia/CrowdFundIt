package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.auth.Role;
import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

//Κλάση REST controller
@RestController
@RequestMapping("/admin/users")
//Μόνο χρήστες με ρόλο ADMIN μπορούν να έχουν πρόσβαση στα endpoints
@PreAuthorize("hasRole('ADMIN')")
//Χρήση του Swagger tag για τεκμηρίωση
@Tag(name = "Admin - Users")
public class AdminUserController {

    //Service που περιέχει τη λογική διαχείρισης χρηστών
    private final UserService users;

    //Constructor injection του UserService
    public AdminUserController(UserService users) {
        this.users = users;
    }


    @Schema(description = "Αίτημα δημιουργίας νέου χρήστη")
    public static class CreateUserRequest {
        @Schema(description = "Όνομα χρήστη", example = "johndoe")
        public String username;
        @Schema(description = "Email χρήστη", example = "john@example.com")
        public String email;
        @Schema(description = "Κωδικός πρόσβασης", example = "password123")
        public String password;
        @Schema(description = "Ρόλοι χρήστη", example = "[\"ROLE_BACKER\"]")
        public Set<String> roles;
    }

    @Schema(description = "Αίτημα ενημέρωσης χρήστη")
    public static class UpdateUserRequest {
        @Schema(description = "Νέο email")
        public String email;
        @Schema(description = "Νέος κωδικός πρόσβασης")
        public String password;
        @Schema(description = "Ενεργός ή όχι")
        public Boolean enabled;
        @Schema(description = "Κλειδωμένος ή όχι")
        public Boolean locked;
        @Schema(description = "Ρόλοι", example = "[\"ROLE_BACKER\"]")
        public Set<String> roles;
    }

    @Schema(description = "Απόκριση με τα δεδομένα χρήστη")
    public static class UserResponse {
        public String id;
        public String username;
        public String email;
        public boolean enabled;
        public boolean locked;
        public Set<String> roles;

        //Μετατροπή από entity User σε DTO UserResponse
        public static UserResponse from(User u) {
            UserResponse r = new UserResponse();
            r.id = u.getId() != null ? u.getId().toString() : null;
            r.username = u.getUsername();
            r.email = u.getEmail();
            r.enabled = u.isEnabled();
            r.locked = u.isLocked();
            r.roles = u.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            return r;
        }
    }

    @Operation(
            summary = "Δημιουργία νέου χρήστη",
            description = "Δημιουργεί νέο χρήστη με καθορισμένους ρόλους",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateUserRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ο χρήστης δημιουργήθηκε", content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )

    //Δημιουργεί νέο χρήστη
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody CreateUserRequest req) {
        var u = users.createUser(req.username, req.email, req.password, req.roles);
        return UserResponse.from(u);
    }

    @Operation(
            summary = "Ενημέρωση χρήστη",
            description = "Τροποποιεί στοιχεία ενός χρήστη με βάση το ID",
            parameters = @Parameter(name = "id", description = "UUID του χρήστη"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UpdateUserRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ο χρήστης ενημερώθηκε", content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )

    //Ενημερώνει έναν υπάρχοντα χρήστη με βάση το ID
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @RequestBody UpdateUserRequest req) {
        var u = users.updateUser(id, req.email, req.password, req.enabled, req.locked, req.roles);
        return UserResponse.from(u);
    }

    @Operation(
            summary = "Διαγραφή χρήστη",
            description = "Διαγράφει χρήστη με βάση το ID",
            parameters = @Parameter(name = "id", description = "UUID του χρήστη"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Ο χρήστης διαγράφηκε"),
                    @ApiResponse(responseCode = "404", description = "Ο χρήστης δεν βρέθηκε")
            }
    )

    //Διαγράφει έναν χρήστη με βάση το ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        users.deleteUser(id);
    }

    @Operation(
            summary = "Λίστα όλων των χρηστών",
            description = "Επιστρέφει όλους τους εγγεγραμμένους χρήστες",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Λίστα χρηστών", content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )

    //Επιστρέφει λίστα με όλους τους χρήστες
    @GetMapping
    public List<UserResponse> list() {
        return users.listUsers().stream().map(UserResponse::from).toList();
    }

    @Operation(
            summary = "Εύρεση χρήστη με ID",
            description = "Επιστρέφει τα στοιχεία χρήστη με βάση το UUID",
            parameters = @Parameter(name = "id", description = "UUID χρήστη"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ο χρήστης βρέθηκε", content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Ο χρήστης δεν βρέθηκε")
            }
    )

    //Επιστρέφει τα δεδομένα ενός χρήστη με βάση το ID
    @GetMapping("/{id}")
    public UserResponse get(@PathVariable UUID id) {
        return UserResponse.from(users.getUser(id));
    }
}
