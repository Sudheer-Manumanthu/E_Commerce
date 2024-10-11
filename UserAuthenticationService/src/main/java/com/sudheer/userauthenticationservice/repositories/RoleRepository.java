package com.sudheer.userauthenticationservice.repositories;

import com.sudheer.userauthenticationservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByRoleName(String roleName);
}
