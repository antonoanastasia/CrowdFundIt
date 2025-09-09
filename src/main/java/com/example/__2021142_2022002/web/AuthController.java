package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.auth.Role;
import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.repo.UserRepository;
import com.example.__2021142_2022002.repo.UserRoleRepository;
import com.example.__2021142_2022002.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Tag(name = "Auth")
public class AuthController {

    private final UserRepository users;
    private final UserRoleRepository roles;
    private final PasswordEncoder enc;
    private final UserService userService;

    public AuthController(UserRepository users,
                          UserRoleRepository roles,
                          PasswordEncoder enc,
                          UserService userService) {
        this.users = users;
        this.roles = roles;
        this.enc = enc;
        this.userService = userService;
    }

    // ===== DTOs =====
    @Schema
    public static class RegisterRequest {
        @Schema public String username;
        @Schema public String email;
        @Schema public String password;
        @Schema public String role; // "CREATOR" ή "BACKER" (προαιρετικό)
    }

    @Schema
    public static class UserResponse {
        public String id;
        public String username;
        public String email;
        public Set<String> roles;
        public UserResponse(User u) {
            this.id = u.getId() != null ? u.getId().toString() : null;
            this.username = u.getUsername();
            this.email = u.getEmail();
            this.roles = u.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        }
    }

    // ===== JSON API: POST /api/auth/register =====
    @Operation(
            summary = "Εγγραφή νέου χρήστη (JSON)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, content = @Content(schema = @Schema(implementation = RegisterRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request")
            }
    )
    @PostMapping("/api/auth/register")
    @ResponseBody
    public UserResponse register(@RequestBody RegisterRequest req) {
        if (req.username == null || req.username.isBlank()) throw new IllegalArgumentException("username is required");
        if (req.email == null || req.email.isBlank())       throw new IllegalArgumentException("email is required");
        if (req.password == null || req.password.isBlank()) throw new IllegalArgumentException("password is required");
        if (users.existsByUsername(req.username))           throw new IllegalArgumentException("username already exists");
        if (users.existsByEmail(req.email))                 throw new IllegalArgumentException("email already exists");

        String roleName = (req.role == null || req.role.isBlank())
                ? "ROLE_BACKER"
                : ("ROLE_" + req.role.trim().toUpperCase());

        Role role = roles.findByName(roleName).orElseGet(() -> roles.save(new Role(null, roleName)));

        User u = new User();
        u.setUsername(req.username.trim());
        u.setEmail(req.email.trim());
        u.setPasswordHash(enc.encode(req.password)); // BCrypt
        u.setRoles(Set.of(role));
        users.save(u);

        return new UserResponse(u);
    }

    // ===== Φόρμα: POST /register =====
    @PostMapping("/register")
    public String doRegister(@ModelAttribute("user") User user) {
        userService.registerNewUser(user); // κάνει encode & save
        return "redirect:/login";
    }
}

