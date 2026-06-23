package org.subscribe.master.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.subscribe.master.entities.AuthUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AuthUser,Long> {
    Optional<AuthUser> findByEmail(String email);
}
