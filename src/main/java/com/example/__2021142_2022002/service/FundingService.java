package com.example.__2021142_2022002.service;

import com.example.__2021142_2022002.auth.User;
import com.example.__2021142_2022002.domain.Funding;
import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.repo.FundingRepository;
import com.example.__2021142_2022002.repo.ProjectRepository;
import com.example.__2021142_2022002.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class FundingService {

    @Autowired
    private FundingRepository fundingRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public Funding fundProject(Long projectId, String username, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount must be positive");
        }

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        User backer = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Funding funding = new Funding();
        funding.setProject(project);
        funding.setBacker(backer);
        funding.setAmount(amount);
        funding.setConfirmed(true);  // άμεσα επιβεβαιωμένο, ή βάλε false αν θες να υπάρχει approve
        Funding saved = fundingRepo.save(funding);

        project.setCollectedAmount(project.getCollectedAmount().add(amount));
        projectRepo.save(project);

        return saved;
    }
}
