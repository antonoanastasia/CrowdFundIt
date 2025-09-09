package com.example.__2021142_2022002.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Support {
    @Id @GeneratedValue private Long id;

    @ManyToOne(optional=false) private Project project;

    // για το part σου αρκεί username (αν αργότερα βάλετε User entity, το αλλάζεις)
    @NotBlank private String backerUsername;

    @NotNull @Positive @Column(precision=19, scale=2)
    private BigDecimal amount;

    private Instant supportedAt = Instant.now();
}