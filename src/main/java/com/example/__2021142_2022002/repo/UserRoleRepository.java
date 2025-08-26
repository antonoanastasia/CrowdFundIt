package com.example.__2021142_2022002.repo;

// Importing the Role entity from the authentication package
import com.example.__2021142_2022002.auth.Role;

// Importing JpaRepository to enable CRUD operations and query derivation
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Role entities from the database. Extends JpaRepository to provide standard CRUD operations. Spring Data JPA will automatically generate the implementation at runtime.
 */
public interface UserRoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
