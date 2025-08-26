package com.example.__2021142_2022002.auth;

import jakarta.persistence.*;

//Η κλάση είναι οντότητα JPA,θα αντιστοιχιστεί σε πίνακα βάσης δεδομένων
@Entity
//Η οντότητα αντιστοιχεί στον πίνακα με όνομα roles
@Table(name = "roles")
public class Role {

    //Πρωτεύον κλειδί
    @Id
    //Αυτόματη τιμη id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Περιορισμοί της στήλης name

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    //Προεπιλεγμένος constructor
    public Role() {
    }

    //Constructor για αρχικοποίηση του ρόλου
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Επιστρέφει μια συμβολοσειρά που περιγράφει το αντικείμενο Role
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
