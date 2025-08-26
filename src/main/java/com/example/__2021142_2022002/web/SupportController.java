package com.example.__2021142_2022002.web;

import com.example.__2021142_2022002.domain.Project;
import com.example.__2021142_2022002.service.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/support")
@Tag(name="Support")
public class SupportController {

    private final SupportService svc;

    public SupportController(SupportService svc) {
        this.svc = svc;
    }

    @Schema(description = "Αίτημα υποστήριξης έργου")
    public record SupportReq(@Schema(description = "Ποσό υποστήριξης σε ευρώ", example = "50.00")
                                 BigDecimal amount){}

    @Operation(
            summary = "Δέσμευση ποσού για έργο (pledge)",
            description = "Ο χρήστης υποστηρίζει ένα έργο με συγκεκριμένο ποσό. Διαθέσιμο μόνο για BACKER.",
            parameters = {
                    @Parameter(name = "projectId", description = "ID του έργου που θέλει να υποστηρίξει", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Ποσό χρηματοδότησης",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SupportReq.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Η υποστήριξη καταχωρήθηκε", content = @Content(schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "403", description = "Ο χρήστης δεν έχει ρόλο BACKER"),
                    @ApiResponse(responseCode = "404", description = "Το έργο δεν βρέθηκε ή δεν είναι διαθέσιμο")
            }
    )
    @PostMapping("/{projectId}")
    @PreAuthorize("hasRole('BACKER')")
    public Project pledge(@PathVariable Long projectId, @RequestBody SupportReq req, Principal me){
        return svc.support(projectId, req.amount(), me != null ? me.getName() : "anonymous");
    }
}
