package com.thedevlair.user.dao;

import com.thedevlair.user.model.business.ERole;
import com.thedevlair.user.model.thirdparty.RoleDTO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface RoleRepository extends R2dbcRepository<RoleDTO, Long> {
    Mono<Optional<RoleDTO>> findByName(ERole name);
}
