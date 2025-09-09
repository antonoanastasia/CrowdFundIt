package com.example.__2021142_2022002.domain;

import com.example.__2021142_2022002.auth.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Funding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Project project;

    @ManyToOne(optional = false)
    private User backer;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    private LocalDateTime fundingDate = LocalDateTime.now();

    private boolean confirmed = false;

    public Long getId() { return id; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    public User getBacker() { return backer; }
    public void setBacker(User backer) { this.backer = backer; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getFundingDate() { return fundingDate; }
    public void setFundingDate(LocalDateTime fundingDate) { this.fundingDate = fundingDate; }
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}
