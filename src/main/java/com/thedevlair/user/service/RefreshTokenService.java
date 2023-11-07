package com.thedevlair.user.service;

import com.thedevlair.user.model.business.RefreshToken;
import com.thedevlair.user.model.business.Rs.RefreshTokenRs;
import org.springframework.http.ResponseEntity;

public interface RefreshTokenService {

    RefreshToken findByToken(String token);

    RefreshToken createRefreshToken(Long id);

    RefreshToken verifyExpiration(RefreshToken refreshToken);

    void deleteByUserId(Long userId);

    ResponseEntity<RefreshTokenRs> refreshToken(RefreshToken refreshToken);
}
