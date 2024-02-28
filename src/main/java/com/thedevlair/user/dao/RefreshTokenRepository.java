package com.thedevlair.user.dao;

import com.thedevlair.user.model.thirdparty.RefreshTokenDTO;
import com.thedevlair.user.model.thirdparty.UserDTO;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RefreshTokenRepository extends R2dbcRepository<RefreshTokenDTO, Long> {
    Mono<RefreshTokenDTO> findByToken(String token);

    void deleteByToken(String token);

    @Modifying
    int deleteByUser(UserDTO user);
}
