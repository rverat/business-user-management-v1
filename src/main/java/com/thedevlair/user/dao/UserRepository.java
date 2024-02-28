package com.thedevlair.user.dao;

import com.thedevlair.user.model.thirdparty.UserDTO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserRepository extends R2dbcRepository<UserDTO, Long> {
    Mono<Optional<UserDTO>> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);
}