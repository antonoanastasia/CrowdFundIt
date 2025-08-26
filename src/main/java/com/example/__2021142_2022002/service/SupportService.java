package com.example.__2021142_2022002.service;

import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.domain.ProjectStatus;
import com.example.__2021142_2022002.domain.Support;
import com.example.__2021142_2022002.repo.ProjectRepository;
import com.example.__2021142_2022002.repo.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SupportService {

    @Autowired
    private ProjectRepository projects; // ΠΡΟΣΟΧΗ: όχι final

    @Autowired
    private SupportRepository supports;


    /** Προσθήκη στήριξης (pledge) */
    @Transactional
    public Project support(Long projectId, BigDecimal amount, String username) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be > 0");
        }

        Project p = projects.findById(projectId).orElseThrow();

        if (p.getStatus() != ProjectStatus.FUNDING) {
            throw new IllegalStateException("Project is not open for funding");
        }

        // ➕ Δημιουργία νέου Support
        Support s = new Support();
        s.setProject(p);
        s.setBackerUsername(username);
        s.setAmount(amount);
        supports.save(s); // αποθήκευση στη βάση

        // ➕ Ενημέρωση ποσού στο έργο
        BigDecimal newTotal = p.getCollectedAmount().add(amount);
        p.setCollectedAmount(newTotal);

        if (newTotal.compareTo(p.getTargetAmount()) >= 0) {
            p.setStatus(ProjectStatus.COMPLETED);
        }

        return p; // @Transactional κάνει persist
    }

    public List<Support> findByBackerUsername(String username) {
        return supports.findByBackerUsername(username);
    }


}

