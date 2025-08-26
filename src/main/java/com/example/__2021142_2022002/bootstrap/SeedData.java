package com.example.__2021142_2022002.bootstrap;

import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.domain.ProjectStatus;
import com.example.__2021142_2022002.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private ProjectRepository projectRepository; // όχι final

    @Override
    public void run(String... args) {
        if (projectRepository.count() > 0) return;

        Project p1 = new Project();
        p1.setTitle("Open-Source Weather App");
        p1.setDescription("Μια open-source εφαρμογή καιρού με live ενημερώσεις.");
        p1.setTargetAmount(new BigDecimal("5000.00"));
        p1.setCollectedAmount(new BigDecimal("1200.00"));
        p1.setStatus(ProjectStatus.FUNDING);

        Project p2 = new Project();
        p2.setTitle("3D Printed Prosthetics");
        p2.setDescription("Χαμηλού κόστους προθέσεις μέσω 3D printing.");
        p2.setTargetAmount(new BigDecimal("10000.00"));
        p2.setCollectedAmount(new BigDecimal("10000.00"));
        p2.setStatus(ProjectStatus.COMPLETED);

        projectRepository.save(p1);
        projectRepository.save(p2);
    }
}
