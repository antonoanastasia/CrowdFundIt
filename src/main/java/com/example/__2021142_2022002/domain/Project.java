package com.example.__2021142_2022002.domain;

import com.example.__2021142_2022002.auth.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

//Η κλάση είναι JPA οντότητα και θα αποθηκεύεται σε βάση δεδομένων
@Entity
//Ορίζει το όνομα του πίνακα ως projects
@Table(name = "projects")
public class Project {

    //Πρωτεύον κλειδί της οντότητας
    @Id
    @GeneratedValue
    private Long id;

    //Τίτλος του έργου
    @NotBlank
    private String title;

    //Περιγραφή του έργου
    @NotBlank
    @Column(length = 4000)
    private String description;

    private java.time.LocalDateTime creationDate = java.time.LocalDateTime.now();

    //Ποσό χρηματοδότησης
    @NotNull
    @Positive
    @Column(precision = 19, scale = 2)
    private BigDecimal targetAmount;

    //Ποσό δεν μπορεί να είναι null και ξεκινά από 0
    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal collectedAmount = BigDecimal.ZERO;

    //Το έργο αποθηκεύεται ως string στη βάση
    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.PENDING;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;


    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public java.time.LocalDateTime getCreationDate() {
        return creationDate;
    }
    public BigDecimal getTargetAmount() { return targetAmount; }
    public BigDecimal getCollectedAmount() { return collectedAmount; }
    public ProjectStatus getStatus() { return status; }
    public Long getVersion() { return version; }
    public User getCreator() { return creator; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCreationDate(java.time.LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public void setTargetAmount(BigDecimal targetAmount) { this.targetAmount = targetAmount; }
    public void setCollectedAmount(BigDecimal collectedAmount) { this.collectedAmount = collectedAmount; }
    public void setStatus(ProjectStatus status) { this.status = status; }
    public void setVersion(Long version) { this.version = version; }
    public void setCreator(User creator) { this.creator = creator; }

    }

