package com.example.__2021142_2022002.bootstrap;

import com.example.__2021142_2022002.auth.Role;
import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.repo.UserRepository;
import com.example.__2021142_2022002.repo.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

//Δηλώνει ότι η κλάση είναι bean του Spring και εκτελείται αυτόματα κατά την εκκίνηση της εφαρμογής
@Component
public class UserSeedData implements CommandLineRunner {

    //Εισαγωγή των repositories και του password encoder
    @Autowired private UserRoleRepository roles;
    @Autowired private UserRepository users;
    @Autowired private PasswordEncoder enc;

    //Η μέθοδος αυτή εκτελείται αυτόματα όταν ξεκινά η εφαρμογή
    @Override
    public void run(String... args) {

        //Εύρεση ή δημιουργία ρόλων
        Role admin   = roles.findByName("ROLE_ADMIN")
                .orElseGet(() -> roles.save(new Role(null, "ROLE_ADMIN")));

        Role creator = roles.findByName("ROLE_CREATOR")
                .orElseGet(() -> roles.save(new Role(null, "ROLE_CREATOR")));

        Role backer  = roles.findByName("ROLE_BACKER")
                .orElseGet(() -> roles.save(new Role(null, "ROLE_BACKER")));

        //Δημιουργία χρήστη "admin" με ρόλο ADMIN
        users.findByUsername("admin").orElseGet(() -> {
            User u = new User();
            u.setUsername("admin");
            u.setEmail("admin@example.com");
            u.setPasswordHash(enc.encode("admin"));
            u.setRoles(Set.of(admin));
            return users.save(u);
        });


        users.findByUsername("maria").orElseGet(() -> {
            User u = new User();
            u.setUsername("maria");
            u.setEmail("maria@example.com");
            u.setPasswordHash(enc.encode("creator"));
            u.setRoles(Set.of(creator));
            return users.save(u);
        });


        users.findByUsername("george").orElseGet(() -> {
            User u = new User();
            u.setUsername("george");
            u.setEmail("george@example.com");
            u.setPasswordHash(enc.encode("backer"));
            u.setRoles(Set.of(backer));
            return users.save(u);
        });
    }
}
