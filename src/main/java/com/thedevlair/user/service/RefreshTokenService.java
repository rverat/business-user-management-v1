package com.thedevlair.user.service;

import com.thedevlair.user.model.business.RefreshToken;
import com.thedevlair.user.model.business.rs.RefreshTokenRs;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface RefreshTokenService {

    Mono<RefreshToken> findByToken(String token);

    Mono<RefreshToken> createRefreshToken(Long id);

    Mono<RefreshToken> verifyExpiration(RefreshToken refreshToken);

    void deleteByUserId(Long userId);

    Mono<ResponseEntity<RefreshTokenRs>> refreshToken(RefreshToken refreshToken);
}
