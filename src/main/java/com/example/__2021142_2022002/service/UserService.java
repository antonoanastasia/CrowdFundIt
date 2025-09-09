package com.example.__2021142_2022002.service;

import com.example.__2021142_2022002.auth.Role;
import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.repo.UserRepository;
import com.example.__2021142_2022002.repo.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    @Autowired private UserRepository users;
    @Autowired private UserRepository userRepository;
    @Autowired private UserRoleRepository roles;
    @Autowired private PasswordEncoder enc;

    /* ========================  CRUD  ======================== */

    /** Δημιουργία χρήστη με ρόλους. Επιστρέφει τον αποθηκευμένο User. */
    public User createUser(String username, String email, String rawPassword, Set<String> roleCodes) {
        if (isBlank(username)) throw new IllegalArgumentException("username is required");
        if (isBlank(email))    throw new IllegalArgumentException("email is required");
        if (isBlank(rawPassword)) throw new IllegalArgumentException("password is required");

        if (users.existsByUsername(username)) throw new IllegalArgumentException("username already exists");
        if (users.existsByEmail(email))       throw new IllegalArgumentException("email already exists");

        Set<Role> roleSet = resolveRoles(roleCodes);

        User u = new User();
        u.setUsername(username.trim());
        u.setEmail(email.trim());
        u.setPasswordHash(enc.encode(rawPassword)); // BCrypt
        u.setEnabled(true);
        u.setLocked(false);
        u.setRoles(roleSet);

        return users.save(u);
    }

    /** Μερική ενημέρωση χρήστη. Δεν αλλάζουμε username. */
    public User updateUser(UUID id,
                           String email,
                           String rawPassword,
                           Boolean enabled,
                           Boolean locked,
                           Set<String> roleCodes) {

        User u = users.findById(id).orElseThrow(() -> new NoSuchElementException("User not found: " + id));

        // email (με έλεγχο μοναδικότητας)
        if (!isBlank(email) && !email.equals(u.getEmail())) {
            if (users.existsByEmail(email)) throw new IllegalArgumentException("email already exists");
            u.setEmail(email.trim());
        }

        // password
        if (!isBlank(rawPassword)) {
            u.setPasswordHash(enc.encode(rawPassword));
        }

        // enabled / locked
        if (enabled != null) u.setEnabled(enabled);
        if (locked  != null) u.setLocked(locked);

        // roles (αν δόθηκαν, αντικαθιστούμε το σύνολο)
        if (roleCodes != null) {
            u.setRoles(resolveRoles(roleCodes));
        }

        return users.save(u);
    }

    /** Διαγραφή χρήστη. */
    public void deleteUser(UUID id) {
        if (!users.existsById(id)) throw new NoSuchElementException("User not found: " + id);
        users.deleteById(id);
    }

    /* ========================  Reads  ======================== */

    public List<User> listUsers() {
        return users.findAll();
    }

    public User getUser(UUID id) {
        return users.findById(id).orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }

    /* =====================  Helpers/Rules  ===================== */

    /** Δέχεται κωδικούς ρόλων τύπου "ADMIN","CREATOR","BACKER" ή ήδη "ROLE_..." και επιστρέφει Role entities. */
    private Set<Role> resolveRoles(Set<String> roleCodes) {
        // default: BACKER αν δεν δοθεί τίποτα
        if (roleCodes == null || roleCodes.isEmpty()) {
            roleCodes = Set.of("BACKER");
        }

        return roleCodes.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::normalizeRoleName)               // π.χ. CREATOR -> ROLE_CREATOR
                .map(this::getOrCreateRole)                 // φέρνει από DB ή το δημιουργεί
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /** Επιστρέφει όνομα ρόλου με πρόθεμα ROLE_. */
    private String normalizeRoleName(String code) {
        String upper = code.toUpperCase(Locale.ROOT);
        return upper.startsWith("ROLE_") ? upper : "ROLE_" + upper;
    }

    /** Βρίσκει ρόλο από DB ή τον δημιουργεί. */
    private Role getOrCreateRole(String roleName) {
        return roles.findByName(roleName).orElseGet(() -> roles.save(new Role(null, roleName)));
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    public String getRoleForUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow()
                .getRoles()
                .stream()
                .findFirst()
                .map(Role::getName)
                .orElse("GUEST");
    }

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void registerNewUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        // όρισε και default ρόλο αν χρειάζεται
        userRepository.save(user);
    }

}
