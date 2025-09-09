package com.example.__2021142_2022002.bootstrap;

import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.domain.ProjectStatus;
import com.example.__2021142_2022002.repo.ProjectRepository;
import com.example.__2021142_2022002.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Order(2) // τρέχει μετά το UserSeedData (που έχει @Order(1))
@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (projectRepository.count() > 0) return;

        // Βρίσκουμε έναν υπάρχοντα creator από το UserSeedData (π.χ. "maria")
        User creator = userRepository.findByUsername("maria")
                .orElseThrow(() -> new IllegalStateException("SeedData: missing seed user 'maria'"));

        Project p1 = new Project();
        p1.setTitle("Open-Source Weather App");
        p1.setDescription("Μια open-source εφαρμογή καιρού με live ενημερώσεις.");
        p1.setTargetAmount(new BigDecimal("5000.00"));
        p1.setCollectedAmount(new BigDecimal("1200.00"));
        p1.setStatus(ProjectStatus.FUNDING);
        p1.setCreator(creator); // κρίσιμο

        Project p2 = new Project();
        p2.setTitle("3D Printed Prosthetics");
        p2.setDescription("Χαμηλού κόστους προθέσεις μέσω 3D printing.");
        p2.setTargetAmount(new BigDecimal("10000.00"));
        p2.setCollectedAmount(new BigDecimal("10000.00"));
        p2.setStatus(ProjectStatus.COMPLETED);
        p2.setCreator(creator); // κρίσιμο

        projectRepository.save(p1);
        projectRepository.save(p2);
    }
}
