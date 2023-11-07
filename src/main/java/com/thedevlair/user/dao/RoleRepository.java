package com.thedevlair.user.dao;

import com.thedevlair.user.model.business.ERole;
import com.thedevlair.user.model.thirdparty.RoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleDTO, Long> {
    Optional<RoleDTO> findByName(ERole name);
}
