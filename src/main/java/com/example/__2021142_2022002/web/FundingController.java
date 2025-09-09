package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.domain.Funding;
import com.example.__2021142_2022002.repo.FundingRepository;
import com.example.__2021142_2022002.service.FundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/funding")
public class FundingController {

    @Autowired
    private FundingService service;

    @Autowired
    private FundingRepository fundingRepo;

    @PostMapping("/{projectId}")
    public Funding fund(@PathVariable Long projectId,
                        @RequestBody BigDecimal amount,
                        @AuthenticationPrincipal UserDetails user) {
        if (user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return service.fundProject(projectId, user.getUsername(), amount);
    }

    @GetMapping("/project/{projectId}")
    public List<Funding> getFundingsForProject(@PathVariable Long projectId) {
        return fundingRepo.findByProject(new com.example.__2021142_2022002.domain.Project() {{
            setId(projectId);
        }});
    }
}
