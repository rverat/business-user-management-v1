package com.thedevlair.user.dao;

import com.thedevlair.user.model.thirdparty.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {
    Optional<UserDTO> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}